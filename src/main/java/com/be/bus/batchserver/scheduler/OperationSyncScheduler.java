package com.be.bus.batchserver.scheduler;

import com.be.bus.domain.operation.entity.Operation;
import com.be.bus.domain.operation.helper.OperationHelper;
import com.be.bus.domain.route.entity.Route;
import com.be.bus.domain.route.helper.RouteHelper;
import com.be.bus.domain.terminal.entity.Terminal;
import com.be.bus.domain.terminal.helper.TerminalHelper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class OperationSyncScheduler {

    private final TerminalHelper terminalHelper;
    private final RouteHelper routeHelper;
    private final OperationHelper operationHelper;
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String URL = "https://txbus.t-money.co.kr/otck/readAlcnList.do";

    @Scheduled(cron = "0 0 0 * * *") // 매일 0시0분0초
    //@Scheduled(fixedDelay = 1000*60*60) // 1시간짜리 즉시실행

    @Transactional
    public void syncOperations() {
        log.info("\\uD83D\\uDE8C 운행정보 동기화 시작!");

        List<RouteTarget> targets = List.of(
                new RouteTarget("5325101", "0671801"),
                new RouteTarget("0671801", "5325101")
        );

        LocalDate today = LocalDate.now();

        for (RouteTarget target : targets) {
            Terminal dep = terminalHelper.findByIdOrElseThrow(target.getDepartureId());
            Terminal arr = terminalHelper.findByIdOrElseThrow(target.getArrivalId());
            Route route = routeHelper.findByDepartureAndArrival(dep, arr);
            int totalInserted = 0;

            for (int i = 0; i < 30; i++) {
                LocalDate targetDate = today.plusDays(i);
                List<OperationInfo> operationInfos = fetchOperations(target, targetDate);

                // 해당 날짜에 존재하는 운행정보를 가져와 map으로 변경
                List<Operation> existingOps = operationHelper.findByRouteAndDate(route, targetDate);
                Map<LocalDateTime, Operation> existingOpMap = existingOps.stream()
                        .collect(Collectors.toMap(Operation::getDepartureDtm, op -> op));
                Set<LocalDateTime> fetchedTimes = new HashSet<>();

                int inserted = 0;

                for (OperationInfo info : operationInfos) {
                    LocalDateTime time = info.getDepartureDateTime();
                    fetchedTimes.add(time);

                    // 이미 DB에 존재하는 운헹정보가 아닌경우(변경, 신규)
                    if (!existingOpMap.containsKey(time)) {
                        operationHelper.saveOperation(info.toEntity(route));
                        inserted++;
                        totalInserted++;
                        //log.info("➕ [신규] {} → {} [{}] 운행 추가됨", dep.getName(), arr.getName(), time);
                    } else {
                        Operation existing = existingOpMap.get(time);
                        if (!isSame(existing, info)) {
                            log.warn("🔄 [변경] {} → {} [{}] 운행 변경 감지", dep.getName(), arr.getName(), time);
                            log.warn("    기존: 회사={}, 타입={}, 소요시간={}, 가격={}",
                                    existing.getBusCompany(), existing.getBusType(), existing.getDuration(), existing.getPrice());
                            log.warn("    응답: 회사={}, 타입={}, 소요시간={}, 가격={}",
                                    info.getBusCompany(), info.getBusType(), info.getDuration(), info.getPrice());
                        }
                    }
                }

                for (Operation existing : existingOps) {
                    if (!fetchedTimes.contains(existing.getDepartureDtm())) {
                        log.warn("❌ [삭제 감지] {} → {} [{}] 운행 사라짐", dep.getName(), arr.getName(), existing.getDuration());
                    }
                }

                log.info("✅ [{}] {} - {} (Route ID: {}) 운행정보 {}건 삽입 완료", targetDate, dep.getName(), arr.getName(), route.getId(), inserted);
            }

            log.info("🎯 {} - {} 운행정보 전체 동기화 완료: 총 {}건 추가됨", dep.getName(), arr.getName(), totalInserted);
        }

        log.info("🎯🎯 운행정보 전체 동기화 완료");
    }

    private boolean isSame(Operation op, OperationInfo info) {
        return op.getBusCompany().equals(info.getBusCompany()) &&
                op.getBusType().equals(info.getBusType()) &&
                op.getDuration().equals(info.getDuration()) &&
                Objects.equals(op.getPrice(), info.getPrice());
    }

    private List<OperationInfo> fetchOperations(RouteTarget target, LocalDate date) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("depr_Trml_Cd", target.getDepartureId());
        params.add("arvl_Trml_Cd", target.getArrivalId());
        params.add("depr_Trml_Nm", "");
        params.add("arvl_Trml_Nm", "");
        params.add("depr_Dt", date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        params.add("bef_Aft_Dvs", "D");
        params.add("req_Rec_Num", "999");
        params.add("depr_Time", "00:00");
        params.add("ig", "1");
        params.add("im", "0");
        params.add("ic", "0");
        params.add("iv", "0");

        try {
            String html = restTemplate.postForObject(URL, params, String.class);
            Document doc = Jsoup.parse(html);
            Elements rows = doc.select("table tbody tr");

            List<OperationInfo> result = new ArrayList<>();

            for (Element row : rows) {
                Elements cols = row.select("td");
                if (cols.size() < 7) continue;

                String departureTimeStr = cols.get(0).text().trim();
                String companyAndDuration = cols.get(1).text().trim();
                String busType = cols.get(2).text().trim();
                String priceStr = cols.get(3).text().replace(",", "").replace("원", "").trim();

                String busCompany = companyAndDuration.split("\\d", 2)[0].replace("\\u00A0", "").trim();
                String duration = companyAndDuration.substring(busCompany.length()).replace("소요 예상", "").trim();

                LocalDateTime departureDateTime = LocalDateTime.parse(
                        date + "T" + departureTimeStr,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
                );

                Integer price = Integer.parseInt(priceStr);

                result.add(OperationInfo.builder()
                        .departureDateTime(departureDateTime)
                        .busCompany(busCompany)
                        .busType(busType)
                        .duration(duration)
                        .price(price)
                        .build());
            }

            return result;
        } catch (Exception e) {
            log.error("❌ 운행정보 가져오기 실패: {} -> {}, {}", target.getDepartureId(), target.getArrivalId(), date, e);
            return List.of();
        }
    }

    @Getter
    @Builder
    private static class OperationInfo {
        private LocalDateTime departureDateTime;
        private String busCompany;
        private String busType;
        private String duration;
        private Integer price;

        public Operation toEntity(Route route) {
            return Operation.create(departureDateTime, busCompany, busType, duration, price, route);
        }
    }

    @Getter
    @Builder
    private static class RouteTarget {
        private final String departureId;
        private final String arrivalId;

        public RouteTarget(String departureId, String arrivalId) {
            this.departureId = departureId;
            this.arrivalId = arrivalId;
        }
    }
}
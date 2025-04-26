package com.be.bus.global.scheduler;

import com.be.bus.domain.operation.entity.Operation;
import com.be.bus.domain.operation.helper.OperationHelper;
import com.be.bus.domain.route.entity.Route;
import com.be.bus.domain.route.helper.RouteHelper;
import com.be.bus.domain.terminal.entity.Terminal;
import com.be.bus.domain.terminal.helper.TerminalHelper;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OperationSyncScheduler {

    private final RouteHelper routeHelper;
    private final OperationHelper operationHelper;
    private final TerminalHelper terminalHelper;
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String URL = "https://txbus.t-money.co.kr/otck/readAlcnList.do";
    private static final String DEPARTURE_TERMINAL_ID = "5325101"; // 거제(고현)
    private static final String ARRIVAL_TERMINAL_ID = "0671801";  // 서울남부
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    //@Scheduled(cron = "0 0 4 * * *") // 매일 새벽 4시
    @Scheduled(fixedDelay = 10000000) // 테스트용
    public void syncOperations() {
        log.info("🚌 운행정보 동기화 시작!");

        LocalDate today = LocalDate.now();
        Terminal departureTerminal = terminalHelper.findByIdOrElseThrow(DEPARTURE_TERMINAL_ID);
        Terminal arrivalTerminal = terminalHelper.findByIdOrElseThrow(ARRIVAL_TERMINAL_ID);
        Route route = routeHelper.findByDepartureAndArrival(departureTerminal, arrivalTerminal);

        int totalInserted = 0;

        for (int i = 0; i < 30; i++) {
            LocalDate targetDate = today.plusDays(i);
            List<OperationInfo> operations = fetchOperations(targetDate);

            int dayInserted = 0;
            for (OperationInfo op : operations) {
                if (operationHelper.existsByRouteAndDepartureDateTime(route, op.getDepartureDateTime())) {
                    continue; // 이미 존재하면 skip
                }
                operationHelper.saveOperation(op.toEntity(route));
                dayInserted++;
                totalInserted++;
            }

            log.info("✅ [{}] 운행정보 {}건 삽입 완료", targetDate, dayInserted);
        }

        log.info("🎯 운행정보 전체 동기화 완료: 총 {}건 추가됨", totalInserted);
    }

    private List<OperationInfo> fetchOperations(LocalDate date) {
        MultiValueMap<String, String> payload = new LinkedMultiValueMap<>();
        payload.add("depr_Trml_Cd", DEPARTURE_TERMINAL_ID);
        payload.add("arvl_Trml_Cd", ARRIVAL_TERMINAL_ID);
        payload.add("depr_Trml_Nm", "거제(고현)");
        payload.add("arvl_Trml_Nm", "서울남부");
        payload.add("depr_Dt", date.toString());
        payload.add("bef_Aft_Dvs", "D");
        payload.add("req_Rec_Num", "10");
        payload.add("depr_Time", "00:00");
        payload.add("ig", "1");
        payload.add("im", "0");
        payload.add("ic", "0");
        payload.add("iv", "0");

        try {
            String html = restTemplate.postForObject(URL, payload, String.class);
            Document doc = Jsoup.parse(html);
            Elements rows = doc.select("table tbody tr");

            List<OperationInfo> list = new ArrayList<>();
            for (Element row : rows) {
                Elements cols = row.select("td");
                if (cols.size() < 7) continue;

                String timeStr = cols.get(0).text().trim();
                String operatorRaw = cols.get(1).text().trim();
                String busType = cols.get(2).text().trim();

                if (timeStr.isEmpty() || operatorRaw.isEmpty()) continue;

                LocalTime localTime = LocalTime.parse(timeStr, TIME_FORMATTER);
                LocalDateTime departureDateTime = date.atTime(localTime);

                String busCompany = operatorRaw.replaceAll("(\\d+:\\d+).*", "").trim();
                String duration = operatorRaw.replaceAll(".*?(\\d+:\\d+).*", "$1");

                list.add(OperationInfo.builder()
                        .departureDateTime(departureDateTime)
                        .busCompany(busCompany)
                        .duration(duration)
                        .busType(busType)
                        .build());
            }

            return list;
        } catch (Exception e) {
            log.error("❌ 운행정보 수집 실패: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Getter
    @Builder
    private static class OperationInfo {
        private LocalDateTime departureDateTime;
        private String busCompany;
        private String duration;
        private String busType;

        public Operation toEntity(Route route) {
            return Operation.create(departureDateTime, busCompany, duration, busType, route);
        }
    }
}

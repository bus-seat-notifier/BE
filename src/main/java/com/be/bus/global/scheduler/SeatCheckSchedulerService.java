package com.be.bus.global.scheduler;


import com.be.bus.domain.alertRegisterInfo.enums.SeatAlertType;
import com.be.bus.domain.alertRegisterInfo.helper.AlertRegisterInfoHelper;
import com.be.bus.domain.alertRegisterSeatInfo.entity.AlertRegisterInfo;
import com.be.bus.domain.operation.entity.Operation;
import jakarta.transaction.Transactional;
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

import java.time.LocalTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SeatCheckSchedulerService {

    private final AlertRegisterInfoHelper alertRegisterInfoHelper;
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String URL = "https://txbus.t-money.co.kr/otck/readAlcnList.do";

    @Scheduled(fixedRate = 1000 * 60 * 10) // 10분 마다 실행
    @Transactional
    public void checkSeatAvailability() {
        log.info("🚍 등록된 알림 기준으로 좌석 확인 중...");

        List<AlertRegisterInfo> alertList = alertRegisterInfoHelper.getAllAlertRegisterInfo();
        if (alertList.isEmpty()) {
            log.info("🔕 등록된 알림이 없습니다.");
            return;
        }

        for (AlertRegisterInfo alert : alertList) {
            Operation operation = alert.getOperation();
            SeatAlertType alertType = alert.getSeatAlertType();
            LocalTime targetTime = operation.getDepartureDateTime().toLocalTime();

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("depr_Trml_Cd", operation.getRoute().getDepartureTerminal().getId().toString());
            params.add("arvl_Trml_Cd", operation.getRoute().getArrivalTerminal().getId().toString());
            params.add("depr_Trml_Nm", operation.getRoute().getDepartureTerminal().getName());
            params.add("arvl_Trml_Nm", operation.getRoute().getArrivalTerminal().getName());
            params.add("depr_Dt", operation.getDepartureDateTime().toLocalDate().toString());
            params.add("depr_Time", "00:00");
            params.add("req_Rec_Num", "100");
            params.add("bef_Aft_Dvs", "D");
            params.add("ig", "1");
            params.add("im", "0");
            params.add("ic", "0");
            params.add("iv", "0");

            try {
                String html = restTemplate.postForObject(URL, params, String.class);
                Document doc = Jsoup.parse(html);
                Elements rows = doc.select("table tbody tr");

                if (rows.isEmpty()) {
                    log.warn("❌ [운행 ID: {}] 조회 결과 없음", operation.getId());
                    continue;
                }

                boolean alerted = false;

                for (Element row : rows) {
                    Elements cols = row.select("td");
                    if (cols.size() < 7) continue;

                    String timeStr = cols.get(0).text();
                    LocalTime busTime;
                    try {
                        busTime = LocalTime.parse(timeStr);
                    } catch (Exception e) {
                        log.warn("⚠️ 시간 파싱 실패: {}", timeStr);
                        continue;
                    }

                    // ✅ 시간 조건 체크 (ALL_DAY는 패스, 나머지는 정확히 일치할 때만 진행)
                    if (alertType != SeatAlertType.ALL_DAY && !busTime.equals(targetTime)) continue;

                    String seatHtml = cols.get(6).html();
                    Document seatDoc = Jsoup.parse(seatHtml);
                    Element strongTag = seatDoc.selectFirst("strong");

                    if (strongTag == null) {
                        log.info("❌ [운행 ID: {}] 출발 {} → 예약 불가 또는 매진", operation.getId(), timeStr);
                        continue;
                    }

                    int available = Integer.parseInt(strongTag.ownText().replaceAll("[^0-9]", ""));
                    int total = Integer.parseInt(strongTag.select("em").text().replaceAll("[^0-9]", ""));

                    if (available > 0) {
                        log.info("✅ [운행 ID: {}] 출발 {} → 잔여 좌석 있음! {} / {}", operation.getId(), timeStr, available, total);

                        // 🔔 TODO: 알림 중복 방지 체크 & 전송
                        // fcmService.send(alert.getUser().getFcmToken(), ...);
                        // alert.setLastAlertDtm(LocalDateTime.now());

                        if (alertType == SeatAlertType.ALL_DAY) {
                            break; // 하나만 찾으면 충분
                        }
                    } else {
                        log.info("❌ [운행 ID: {}] 출발 {} → 잔여 없음", operation.getId(), timeStr);
                    }
                }

            } catch (Exception e) {
                log.error("❌ [운행 ID: {}] 좌석 조회 중 예외 발생", operation.getId(), e);
            }
        }
    }
}

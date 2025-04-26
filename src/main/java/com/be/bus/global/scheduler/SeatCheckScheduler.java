//package com.be.bus.global.scheduler;
//
//import jakarta.annotation.PostConstruct;
//import lombok.extern.slf4j.Slf4j;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//@Slf4j
//@Component
//public class SeatCheckScheduler {
//
//    private final RestTemplate restTemplate = new RestTemplate();
//    private static final String URL = "https://txbus.t-money.co.kr/otck/readAlcnList.do";
//    private static final MultiValueMap<String, String> FIXED_PARAMS = new LinkedMultiValueMap<>();
//
//    @PostConstruct
//    public void init() {
//        FIXED_PARAMS.add("depr_Trml_Cd", "0671801");       // 서울남부
//        FIXED_PARAMS.add("arvl_Trml_Cd", "5325101");       // 거제(고현)
//        FIXED_PARAMS.add("depr_Trml_Nm", "서울남부");
//        FIXED_PARAMS.add("arvl_Trml_Nm", "거제(고현)");
//        FIXED_PARAMS.add("depr_Dt", "2025-04-30");         // 원하는 날짜
//        FIXED_PARAMS.add("bef_Aft_Dvs", "D");
//        FIXED_PARAMS.add("req_Rec_Num", "100");            // 노선 최대 개수 늘리기!
//        FIXED_PARAMS.add("depr_Time", "00:00");            // 출발 시간 시작
//        FIXED_PARAMS.add("ig", "1");
//        FIXED_PARAMS.add("im", "0");
//        FIXED_PARAMS.add("ic", "0");
//        FIXED_PARAMS.add("iv", "0");
//    }
//
//    @Scheduled(fixedRate = 60000) // 1분마다 실행
//    public void checkSeatAvailability() {
//        log.info("🚍 좌석 확인 중...");
//
//        String responseHtml = restTemplate.postForObject(URL, FIXED_PARAMS, String.class);
//        Document doc = Jsoup.parse(responseHtml);
//        Elements rows = doc.select("table tbody tr");
//
//        if (rows.isEmpty()) {
//            log.warn("❌ 조회된 노선 정보가 없어요~!");
//            return;
//        }
//
//        for (Element row : rows) {
//            Elements cols = row.select("td");
//            if (cols.size() < 7) continue;
//
//            String time = cols.get(0).text();
//            String company = Jsoup.parse(cols.get(1).html()).text(); // strong + 시간 포함
//            String grade = cols.get(2).text();
//            String adultFare = cols.get(3).text();
//            String childFare = cols.get(4).text();
//
//            // 좌석 정보
//            String seatHtml = cols.get(6).html();
//            Document seatDoc = Jsoup.parse(seatHtml);
//            Element strongTag = seatDoc.selectFirst("strong");
//
//            if (strongTag == null) {
//                log.info("🕒 출발: {}, 🚌 여객사: {}, 상태: ❌ 매진 or 예약S 불가", time, company);
//                continue;
//            }
//
//            String availableSeatText = strongTag.ownText(); // ex: 12석
//            String totalSeatText = strongTag.select("em").text(); // ex: /총21석
//
//            int available = Integer.parseInt(availableSeatText.replaceAll("[^0-9]", ""));
//            int total = Integer.parseInt(totalSeatText.replaceAll("[^0-9]", ""));
//
//            log.info("🕒 출발: {}, 🚌 여객사: {}, 🪑 등급: {}", time, company, grade);
//            log.info("💰 어른: {}, 👶 아동: {}, 🧾 좌석: {} / {}", adultFare, childFare, available, total);
//        }
//    }
//}

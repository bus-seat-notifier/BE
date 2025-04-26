//package com.be.bus.global.scheduler;
//
//import com.be.bus.domain.route.entity.Route;
//import com.be.bus.domain.route.helper.RouteHelper;
//import com.be.bus.domain.terminal.entity.Terminal;
//import com.be.bus.domain.terminal.helper.TerminalHelper;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class RouteSyncScheduler {
//
//    private final TerminalHelper terminalHelper;
//    private final RouteHelper routeHelper;
//    private final RestTemplate restTemplate = new RestTemplate();
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    private static final String URL = "https://txbus.t-money.co.kr/otck/readTrmlList.do";
//
//    //@Scheduled(cron = "0 0 3 * * *") // 매일 새벽 3시
//    @Scheduled(fixedDelay = 10000000) // 테스트용
//    @Transactional
//    public void syncRoutes() {
//        log.info("🚌 노선 동기화 시작!");
//
//        int added = 0;
//        int modified = 0;
//        int total = 0;
//
//        List<TerminalDto> departures = fetchTerminals("01", null);
//        for (TerminalDto dep : departures) {
//            List<TerminalDto> arrivals = fetchTerminals("02", dep.trml_Cd());
//
//            for (TerminalDto arr : arrivals) {
//                total++;
//
//                Terminal departureTerminal = terminalHelper.getOrCreateTerminal(dep.trml_Cd(), dep.trml_Nm(), dep.cty_Bus_Area_Cd());
//                Terminal arrivalTerminal = terminalHelper.getOrCreateTerminal(arr.trml_Cd(), arr.trml_Nm(), arr.cty_Bus_Area_Cd());
//
//                Optional<Route> optionalRoute = routeHelper.findOptionalByDepartureAndArrival(departureTerminal, arrivalTerminal);
//
//                if (optionalRoute.isEmpty()) {
//                    routeHelper.createRoute(departureTerminal, arrivalTerminal);
//                    added++;
//                } else if (routeHelper.isTerminalNameChanged(optionalRoute.get(), dep.trml_Nm(), arr.trml_Nm())) {
//                    log.warn("🔄 [MODIFIED] 노선은 있으나 터미널명 변경됨: {} → {}", dep.trml_Nm(), arr.trml_Nm());
//                    modified++;
//                }
//
//                // ✅ 1000건마다 로그 출력
//                if (total % 1000 == 0) {
//                    log.info("📦 처리 중.. 현재까지 {}건 처리됨 (➕ 추가: {}, 🔄 변경: {})", total, added, modified);
//                }
//            }
//        }
//
//        log.info("🎯 노선 동기화 완료! ➕ {}개 추가, 🔄 {}개 변경 감지, ✅ {}개 처리됨.", added, modified, total);
//    }
//
//
//    private List<TerminalDto> fetchTerminals(String rtnGbn, String preTrmlCd) {
//        MultiValueMap<String, String> payload = new LinkedMultiValueMap<>();
//        payload.add("cty_Bus_Area_Cd", "");
//        payload.add("trml_Nm", "");
//        payload.add("pre_Trml_Cd", preTrmlCd == null ? "" : preTrmlCd);
//        payload.add("rtnGbn", rtnGbn);
//
//        try {
//            String response = restTemplate.postForObject(URL, payload, String.class);
//            return objectMapper.readValue(response, new TypeReference<>() {});
//        } catch (Exception e) {
//            log.error("❌ 터미널 목록 불러오기 실패: rtnGbn={}, preTrmlCd={}", rtnGbn, preTrmlCd, e);
//            return Collections.emptyList();
//        }
//    }
//
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    private record TerminalDto(String trml_Cd, String trml_Nm, String cty_Bus_Area_Cd) {}
//}

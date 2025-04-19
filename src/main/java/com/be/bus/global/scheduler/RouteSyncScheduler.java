package com.be.bus.global.scheduler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RouteSyncScheduler {

    private final JdbcTemplate jdbcTemplate;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String URL = "https://txbus.t-money.co.kr/otck/readTrmlList.do";

    @Scheduled(cron = "0 0 3 * * *") // 매일 새벽 3시 실행
    //@Scheduled(fixedDelay = 100000) // 테스트용
    public void syncRoutes() {
        log.info("🚌 노선 동기화 시작!");

        int added = 0;
        int modified = 0;
        int total = 0;

        List<TerminalDto> departures = fetchTerminals("01", null);
        for (TerminalDto dep : departures) {
            List<TerminalDto> arrivals = fetchTerminals("02", dep.trml_Cd());

            for (TerminalDto arr : arrivals) {
                total++;

                // 터미널 정보 INSERT IGNORE
                insertTerminalIfNotExist(dep);
                insertTerminalIfNotExist(arr);

                // 노선 존재 여부 확인
                if (!routeExists(dep.trml_Cd(), arr.trml_Cd())) {
                    jdbcTemplate.update("""
                        INSERT INTO route (departure_terminal_id, arrival_terminal_id, name)
                        VALUES (?, ?, ?)
                    """, dep.trml_Cd(), arr.trml_Cd(), dep.trml_Nm() + " → " + arr.trml_Nm());

                    //log.info("🆕 [NEW] 노선 추가됨: {} → {}", dep.trml_Nm(), arr.trml_Nm());
                    added++;
                } else if (routeTerminalNameChanged(dep, arr)) {
                    log.warn("🔄 [MODIFIED] 노선은 있으나 터미널명 변경됨: {} → {}", dep.trml_Nm(), arr.trml_Nm());
                    modified++;
                }
            }
        }

        log.info("🎯 노선 동기화 완료! ➕ {}개 추가, 🔄 {}개 변경 감지, ✅ {}개 처리됨.", added, modified, total);
    }

    private List<TerminalDto> fetchTerminals(String rtnGbn, String preTrmlCd) {
        MultiValueMap<String, String> payload = new LinkedMultiValueMap<>();
        payload.add("cty_Bus_Area_Cd", "");
        payload.add("trml_Nm", "");
        payload.add("pre_Trml_Cd", preTrmlCd == null ? "" : preTrmlCd);
        payload.add("rtnGbn", rtnGbn);

        try {
            String response = restTemplate.postForObject(URL, payload, String.class);
            return objectMapper.readValue(response, new TypeReference<>() {});
        } catch (Exception e) {
            log.error("❌ 터미널 목록 불러오기 실패: rtnGbn={}, preTrmlCd={}", rtnGbn, preTrmlCd, e);
            return Collections.emptyList();
        }
    }

    private void insertTerminalIfNotExist(TerminalDto terminal) {
        jdbcTemplate.update("""
            INSERT IGNORE INTO terminal (id, name, area_cd)
            VALUES (?, ?, ?)
        """, terminal.trml_Cd(), terminal.trml_Nm(), terminal.cty_Bus_Area_Cd());
    }

    private boolean routeExists(String depId, String arrId) {
        Integer count = jdbcTemplate.queryForObject("""
            SELECT COUNT(*) FROM route
            WHERE departure_terminal_id = ? AND arrival_terminal_id = ?
        """, Integer.class, depId, arrId);
        return count != null && count > 0;
    }

    private boolean routeTerminalNameChanged(TerminalDto dep, TerminalDto arr) {
        Integer count = jdbcTemplate.queryForObject("""
            SELECT COUNT(*) FROM route r
            JOIN terminal t1 ON r.departure_terminal_id = t1.id
            JOIN terminal t2 ON r.arrival_terminal_id = t2.id
            WHERE r.departure_terminal_id = ? AND r.arrival_terminal_id = ?
              AND (t1.name <> ? OR t2.name <> ?)
        """, Integer.class, dep.trml_Cd(), arr.trml_Cd(), dep.trml_Nm(), arr.trml_Nm());
        return count != null && count > 0;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record TerminalDto(String trml_Cd, String trml_Nm, String cty_Bus_Area_Cd) {}
}

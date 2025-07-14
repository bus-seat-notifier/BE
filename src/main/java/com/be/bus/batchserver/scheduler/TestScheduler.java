//package com.be.bus.batchserver.scheduler;
//
//import com.be.bus.domain.operation.entity.Operation;
//import com.be.bus.domain.operation.helper.OperationHelper;
//import com.be.bus.domain.route.entity.Route;
//import com.be.bus.domain.route.helper.RouteHelper;
//import com.be.bus.domain.terminal.entity.Terminal;
//import com.be.bus.domain.terminal.helper.TerminalHelper;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class TestScheduler {
//
//    // 테스트용
//    @Scheduled(cron = "*/5 * * * * *") // 매일 00시05분
//    @Transactional
//    public void test() {
//        log.info("배치 잘 도니?");
//    }
//}

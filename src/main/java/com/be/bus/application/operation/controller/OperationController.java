package com.be.bus.application.operation.controller;

import com.be.bus.domain.operation.dto.res.MonthlyOperationResDto;
import com.be.bus.domain.operation.service.GetMonthlyOperationByRouteService;

import com.be.bus.global.dto.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/operation")
public class OperationController {
    // 루트를 선택하면 1달치 운행정보를 주도록
    private final GetMonthlyOperationByRouteService getMonthlyOperationByRouteService;

    @Operation(summary = "특정 노선 운행정보 조회", description = "특정 노선 운행정보 목록을 조회합니다.")
    @GetMapping("/")
    public ResponseEntity<SuccessResponse<?>> getOperationByRoute(
            @RequestParam(name = "routeId") Long routeId
    ){
        MonthlyOperationResDto res = getMonthlyOperationByRouteService.execute(routeId);
        return SuccessResponse.ok(res);
    }

}

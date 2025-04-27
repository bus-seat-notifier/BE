package com.be.bus.application.terminal.contoller;

import com.be.bus.domain.terminal.dto.res.ArrivalTerminalResDto;
import com.be.bus.domain.terminal.dto.res.DepartureTerminalResDto;
import com.be.bus.domain.terminal.service.GetArrivalTerminalService;
import com.be.bus.domain.terminal.service.GetDepartureTerminalService;
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
@RequestMapping("/api/terminal")
public class TerminalController {
    private final GetDepartureTerminalService getDepartureTerminalService;
    private final GetArrivalTerminalService getArrivalTerminalService;

    @Operation(summary = "출발 터미널 조회", description = "출발 가능한 터미널 목록을 조회합니다.")
    @GetMapping("/departure")
    ResponseEntity<SuccessResponse<?>> getDepartureTerminal() {
        DepartureTerminalResDto res = getDepartureTerminalService.execute();
        return SuccessResponse.ok(res);
    }

    @Operation(summary = "도착 터미널 조회", description = "도착 가능한 터미널 목록을 조회합니다.")
    @GetMapping("/arrival")
    ResponseEntity<SuccessResponse<?>> getArrivalTerminal(
            @RequestParam(name = "departureTerminalId") String departureTerminalId
    ) {
        ArrivalTerminalResDto res = getArrivalTerminalService.execute(departureTerminalId);
        return SuccessResponse.ok(res);
    }
}

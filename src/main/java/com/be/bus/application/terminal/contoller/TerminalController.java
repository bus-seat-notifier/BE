package com.be.bus.application.terminal.contoller;

import com.be.bus.domain.terminal.dto.res.DepartureTerminalResDto;
import com.be.bus.domain.terminal.service.GetDepartureTerminalService;
import com.be.bus.global.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/terminal")
public class TerminalController {
    private final GetDepartureTerminalService getDepartureTerminalService;
    @GetMapping("/departure")
    ResponseEntity<SuccessResponse<?>> getDepartureTerminal() {
        DepartureTerminalResDto res = getDepartureTerminalService.execute();
        return SuccessResponse.ok(res);
    }
}

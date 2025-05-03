package com.be.bus.application.alertregisterinfo;

import com.be.bus.domain.alertRegisterInfo.service.SaveAlertRegisterInfoService;
import com.be.bus.domain.alertRegisterInfo.dto.req.SaveAlertRegisterInfoReqDto;
import com.be.bus.global.dto.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/alert-register-info")
public class AlertRegisterInfoController {
    private final SaveAlertRegisterInfoService saveAlertRegisterInfoService;
    @Operation(summary = "알림등록정보 등록", description = "알림등록정보를 등록합니다.")
    @PostMapping("/")
    public ResponseEntity<SuccessResponse<?>> saveAlertRegisterInfo(
            @RequestBody final SaveAlertRegisterInfoReqDto req){
        saveAlertRegisterInfoService.execute(req);
        return SuccessResponse.ok("s");
    }
}

package com.be.bus.domain.alertRegisterInfo.dto.req;

import com.be.bus.domain.alertRegisterSeatInfo.dto.AlertRegisterSeatInfoDto;
import lombok.Getter;


public record SaveAlertRegisterInfoReqDto(
        Long operationId,
        Long userId,
        String seatAlertType, // 좌석전체 (or 창가석,2인석,특정번호...)
        AlertRegisterSeatInfoDto alertRegisterSeatInfoDto
) {
}

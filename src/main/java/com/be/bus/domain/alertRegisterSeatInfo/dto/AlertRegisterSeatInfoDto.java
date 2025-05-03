package com.be.bus.domain.alertRegisterSeatInfo.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
public record AlertRegisterSeatInfoDto(
        Long alertRegisterInfoId,
        Long SeatNumber
) {
}

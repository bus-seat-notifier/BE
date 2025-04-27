package com.be.bus.domain.operation.dto.res;

import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder(access = AccessLevel.PRIVATE)
public record OperationDto(
    LocalTime departureTime,
    String busCompany,
    String busType,
    String duration,
    Integer price
) {
    static public OperationDto of(
            LocalTime departureTime,
            String busCompany,
            String busType,
            String duration,
            Integer price
    ) {
        return OperationDto.builder()
                .departureTime(departureTime)
                .busCompany(busCompany)
                .busType(busType)
                .duration(duration)
                .price(price)
                .build();
    }
}

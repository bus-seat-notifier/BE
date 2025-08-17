package com.be.bus.domain.terminal.dto.res;

import com.be.bus.domain.route.entity.Route;
import com.be.bus.domain.terminal.dto.ArrivalTerminalDto;
import com.be.bus.domain.terminal.dto.TerminalDto;
import com.be.bus.domain.terminal.entity.Terminal;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record ArrivalTerminalResDto(
        List<ArrivalTerminalDto> terminalList
) {
    public static ArrivalTerminalResDto of(List<Route> routes) {
        List<ArrivalTerminalDto> arrivalTerminalDtos = routes.stream()
                .map(ArrivalTerminalDto::of)
                .toList();

        return ArrivalTerminalResDto.builder()
                .terminalList(arrivalTerminalDtos)
                .build();
    }


}
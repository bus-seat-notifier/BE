package com.be.bus.domain.terminal.dto;

import com.be.bus.domain.route.entity.Route;
import com.be.bus.domain.terminal.entity.Terminal;
import lombok.AccessLevel;
import lombok.Builder;


@Builder(access = AccessLevel.PRIVATE)
public record ArrivalTerminalDto(
        String id,
        String name,
        String areaCode,
        Long routeId
) {
    public static ArrivalTerminalDto of(Route route) {
        Terminal terminal = route.getArrivalTerminal();
        return ArrivalTerminalDto.builder()
                .id(terminal.getId())
                .name(terminal.getName())
                .areaCode(terminal.getAreaCode())
                .routeId(route.getId())
                .build();
    }
}

package com.be.bus.domain.terminal.dto;

import com.be.bus.domain.terminal.entity.Terminal;
import lombok.AccessLevel;
import lombok.Builder;


@Builder(access = AccessLevel.PRIVATE)
public record TerminalDto(
        String id,
        String name,
        String areaCode
) {
    public static TerminalDto of(Terminal terminal) {
        return TerminalDto.builder()
                .id(terminal.getId())
                .name(terminal.getName())
                .areaCode(terminal.getAreaCode())
                .build();
    }
}

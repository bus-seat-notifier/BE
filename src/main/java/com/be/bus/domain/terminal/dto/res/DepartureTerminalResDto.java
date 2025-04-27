package com.be.bus.domain.terminal.dto.res;

import com.be.bus.domain.terminal.entity.Terminal;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record DepartureTerminalResDto(
        List<TerminalDto> terminalList
) {
    public static DepartureTerminalResDto of(List<Terminal> allDepartureTerminals) {
        List<TerminalDto> terminalDtos = allDepartureTerminals.stream()
                .map(TerminalDto::of)
                .toList();

        return DepartureTerminalResDto.builder()
                .terminalList(terminalDtos)
                .build();
    }


}
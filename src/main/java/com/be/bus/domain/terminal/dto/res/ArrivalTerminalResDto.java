package com.be.bus.domain.terminal.dto.res;

import com.be.bus.domain.terminal.entity.Terminal;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record ArrivalTerminalResDto(
        List<TerminalDto> terminalList
) {
    public static ArrivalTerminalResDto of(List<Terminal> allArrivalTerminals) {
        List<TerminalDto> terminalDtos = allArrivalTerminals.stream()
                .map(TerminalDto::of)
                .toList();

        return ArrivalTerminalResDto.builder()
                .terminalList(terminalDtos)
                .build();
    }


}
package com.be.bus.domain.terminal.service;

import com.be.bus.domain.terminal.dto.res.DepartureTerminalResDto;
import com.be.bus.domain.terminal.entity.Terminal;
import com.be.bus.domain.terminal.helper.TerminalHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class GetDepartureTerminalService {
    private final TerminalHelper terminalHelper;

    public DepartureTerminalResDto execute() {
        List<Terminal> allDepartureTerminals = terminalHelper.getAllDepartureTerminals();
        return DepartureTerminalResDto.of(allDepartureTerminals);
    }
}

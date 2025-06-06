package com.be.bus.domain.terminal.helper;

import com.be.bus.domain.route.entity.Route;
import com.be.bus.domain.route.repository.RouteRepository;
import com.be.bus.domain.terminal.entity.Terminal;
import com.be.bus.domain.terminal.error.TerminalErrorCode;
import com.be.bus.domain.terminal.repository.TerminalRepository;
import com.be.bus.global.error.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TerminalHelper {

    private final TerminalRepository terminalRepository;

    public Terminal findByIdOrElseThrow(String id) {
        return terminalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(TerminalErrorCode.TERMINAL_ENTITY_NOT_FOUND));
    }


    public Terminal getOrCreateTerminal(String id, String name, String areaCode, String departureYn) {
        return terminalRepository.findById(id).orElseGet(() -> {
            Terminal terminal = Terminal.create(id, name, areaCode, departureYn);
            return terminalRepository.save(terminal);
        });
    }

    public List<Terminal> getAllDepartureTerminals() {
        return terminalRepository.findAllByDepartureYn("Y");
    }
}
package com.be.bus.domain.terminal.helper;

import com.be.bus.domain.terminal.entity.Terminal;
import com.be.bus.domain.terminal.repository.TerminalQueryRepository;
import com.be.bus.domain.terminal.repository.TerminalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TerminalHelper {

    private final TerminalRepository terminalRepository;

    public Optional<Terminal> getTerminalByName(String name) {
        return terminalRepository.findByName(name);
    }

    // 필요 시 복잡한 로직 조합 가능!
}

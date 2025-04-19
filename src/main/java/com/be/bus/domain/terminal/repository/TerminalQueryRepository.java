package com.be.bus.domain.terminal.repository;

import com.be.bus.domain.terminal.entity.Terminal;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface TerminalQueryRepository {
    Optional<Terminal> findByName(String name);
}

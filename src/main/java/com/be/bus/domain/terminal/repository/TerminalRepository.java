package com.be.bus.domain.terminal.repository;

import com.be.bus.domain.terminal.entity.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal,String>, TerminalQueryRepository{
}

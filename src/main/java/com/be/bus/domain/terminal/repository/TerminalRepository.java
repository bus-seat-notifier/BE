package com.be.bus.domain.terminal.repository;

import com.be.bus.domain.terminal.entity.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal,String>, TerminalQueryRepository{
    // 출발 터미널 조회
    List<Terminal> findAllByDepartureYn(String departureYn);

}

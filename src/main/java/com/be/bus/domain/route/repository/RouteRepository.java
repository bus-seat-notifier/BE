package com.be.bus.domain.route.repository;

import com.be.bus.domain.route.entity.Route;
import com.be.bus.domain.route.repository.RouteQueryRepository;
import com.be.bus.domain.terminal.entity.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, Long>, RouteQueryRepository {
    Optional<Route> findByDepartureTerminalAndArrivalTerminal(Terminal dep, Terminal arr);

    List<Route> findByDepartureTerminal(Terminal dep);
}

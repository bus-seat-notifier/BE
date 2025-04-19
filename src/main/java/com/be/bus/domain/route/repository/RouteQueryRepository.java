package com.be.bus.domain.route.repository;

import com.be.bus.domain.route.entity.Route;
import com.be.bus.domain.terminal.entity.Terminal;

import java.util.Optional;

public interface RouteQueryRepository {
    Optional<Route> findByDepartureAndArrival(Terminal departure, Terminal arrival);
}

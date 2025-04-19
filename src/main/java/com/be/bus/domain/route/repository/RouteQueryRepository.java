package com.be.bus.domain.route.repository;

import com.be.bus.domain.route.entity.Route;

import java.util.Optional;

public interface RouteQueryRepository {
    Optional<Route> findByDepartureAndArrival(String departureId, String arrivalId);
}

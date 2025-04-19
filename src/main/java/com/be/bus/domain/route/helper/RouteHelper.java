package com.be.bus.domain.route.helper;

import com.be.bus.domain.route.entity.Route;
import com.be.bus.domain.route.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RouteHelper {

    private final RouteRepository routeRepository;

    public Optional<Route> getByDepartureAndArrival(String departureId, String arrivalId) {
        return routeRepository.findByDepartureAndArrival(departureId, arrivalId);
    }
}

package com.be.bus.domain.route.helper;

import com.be.bus.domain.route.entity.Route;
import com.be.bus.domain.route.repository.RouteRepository;
import com.be.bus.domain.terminal.entity.Terminal;
import com.be.bus.global.enums.ErrorCode;
import com.be.bus.global.enums.GlobalErrorCode;
import com.be.bus.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RouteHelper {

    private final RouteRepository routeRepository;

    public Route findByIdOrElseThrow(Long routeId) {
        return routeRepository.findById(routeId)
                .orElseThrow(() -> new EntityNotFoundException(GlobalErrorCode.ENTITY_NOT_FOUND));
    }

    public Optional<Route> findOptionalByDepartureAndArrival(Terminal departure, Terminal arrival) {
        return routeRepository.findByDepartureAndArrival(departure, arrival);
    }

    public Route findByDepartureAndArrival(Terminal departure, Terminal arrival) {
        return findOptionalByDepartureAndArrival(departure, arrival)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    public Route createRoute(Terminal departure, Terminal arrival) {
        Route route = Route.create(departure, arrival);
        return routeRepository.save(route);
    }

    public boolean isTerminalNameChanged(Route route, String depName, String arrName) {
        return !(route.getDepartureTerminal().getName().equals(depName) &&
                route.getArrivalTerminal().getName().equals(arrName));
    }

    public List<Route> getRoutesByDepartureTerminal(Terminal departureTerminal){
        return  routeRepository.findByDepartureTerminal(departureTerminal);
    }
}

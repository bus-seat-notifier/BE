package com.be.bus.domain.route.repository;

import com.be.bus.domain.route.entity.QRoute;
import com.be.bus.domain.route.entity.Route;
import com.be.bus.domain.route.repository.RouteQueryRepository;
import com.be.bus.domain.terminal.entity.Terminal;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RouteQueryRepositoryImpl implements RouteQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Route> findByDepartureAndArrival(Terminal departure, Terminal arrival) {
        QRoute route = QRoute.route;

        Route result = queryFactory
                .selectFrom(route)
                .where(
                        route.departureTerminal.eq(departure),
                        route.arrivalTerminal.eq(arrival)
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }
}

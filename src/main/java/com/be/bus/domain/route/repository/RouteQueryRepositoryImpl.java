package com.be.bus.domain.route.repository;

import com.be.bus.domain.route.entity.QRoute;
import com.be.bus.domain.route.entity.Route;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RouteQueryRepositoryImpl implements RouteQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Route> findByDepartureAndArrival(String departureId, String arrivalId) {
        QRoute route = QRoute.route;

        Route result = queryFactory
                .selectFrom(route)
                .where(
                        route.departureTerminal.id.eq(departureId),
                        route.arrivalTerminal.id.eq(arrivalId)
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }
}

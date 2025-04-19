package com.be.bus.domain.route.repository;

import com.be.bus.domain.route.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long>, RouteQueryRepository {
}

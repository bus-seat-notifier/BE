package com.be.bus.domain.operation.repository;

import com.be.bus.domain.operation.entity.Operation;
import com.be.bus.domain.route.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
    boolean existsByRouteAndDepartureDateTime(Route route, LocalDateTime departureDateTime);
}

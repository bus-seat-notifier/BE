package com.be.bus.domain.operation.helper;

import com.be.bus.domain.operation.entity.Operation;
import com.be.bus.domain.operation.repository.OperationRepository;
import com.be.bus.domain.route.entity.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OperationHelper {

    private final OperationRepository operationRepository;

    public Operation saveOperation(Operation operation) {
        return operationRepository.save(operation);
    }

    public boolean existsByRouteAndDepartureDateTime(Route route, LocalDateTime departureDateTime) {
        return operationRepository.existsByRouteAndDepartureDateTime(route, departureDateTime);
    }

    public List<Operation> findMonthlyByRoute(Route route) {
        LocalDateTime now = LocalDate.now().atStartOfDay();
        LocalDateTime dest = LocalDate.now().plusMonths(1).atTime(23, 59, 59);
        return operationRepository.findByRouteAndDepartureDateTimeBetween(route, now, dest);
    }
}

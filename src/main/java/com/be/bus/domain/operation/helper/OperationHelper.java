package com.be.bus.domain.operation.helper;

import com.be.bus.domain.operation.entity.Operation;
import com.be.bus.domain.operation.repository.OperationRepository;
import com.be.bus.domain.route.entity.Route;
import com.be.bus.global.enums.ErrorCode;
import com.be.bus.global.enums.GlobalErrorCode;
import com.be.bus.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OperationHelper {

    private final OperationRepository operationRepository;

    public Operation findByIdOrElseThrow(Long OperationId) {
        return operationRepository.findById(OperationId)
                .orElseThrow(() -> new EntityNotFoundException(GlobalErrorCode.ENTITY_NOT_FOUND));
    }
    public Operation saveOperation(Operation operation) {
        return operationRepository.save(operation);
    }

    public boolean existsByRouteAndDepartureDateTime(Route route, LocalDateTime departureDtm) {
        return operationRepository.existsByRouteAndDepartureDtm(route, departureDtm);
    }

    public List<Operation> findMonthlyByRoute(Route route) {
        LocalDateTime now = LocalDate.now().atStartOfDay();
        LocalDateTime dest = LocalDate.now().plusMonths(1).atTime(23, 59, 59);
        return operationRepository.findByRouteAndDepartureDtmBetween(route, now, dest);
    }
}

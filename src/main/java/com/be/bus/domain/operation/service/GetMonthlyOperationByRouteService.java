package com.be.bus.domain.operation.service;

import com.be.bus.domain.operation.dto.res.MonthlyOperationResDto;
import com.be.bus.domain.operation.entity.Operation;
import com.be.bus.domain.operation.helper.OperationHelper;
import com.be.bus.domain.route.entity.Route;
import com.be.bus.domain.route.helper.RouteHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class GetMonthlyOperationByRouteService {
    private final OperationHelper operationHelper;
    private final RouteHelper routeHelper;
    public MonthlyOperationResDto execute(Long routeId) {
        Route route = routeHelper.findByIdOrElseThrow(routeId);
        List<Operation> monthlyByRoute = operationHelper.findMonthlyByRoute(route);
        return MonthlyOperationResDto.of(monthlyByRoute);

    }
}

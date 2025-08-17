package com.be.bus.domain.terminal.service;

import com.be.bus.domain.route.entity.Route;
import com.be.bus.domain.route.helper.RouteHelper;
import com.be.bus.domain.terminal.dto.ArrivalTerminalDto;
import com.be.bus.domain.terminal.dto.res.ArrivalTerminalResDto;
import com.be.bus.domain.terminal.entity.Terminal;
import com.be.bus.domain.terminal.helper.TerminalHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class GetArrivalTerminalService {
    private final RouteHelper routeHelper;
    private final TerminalHelper terminalHelper;

    public ArrivalTerminalResDto execute(String departureTerminalId) {
        Terminal departureTerminal = terminalHelper.findByIdOrElseThrow(departureTerminalId);
        List<Route> routesByDepartureTerminal = routeHelper.getRoutesByDepartureTerminal(departureTerminal);

        return ArrivalTerminalResDto.of(routesByDepartureTerminal);
    }
}

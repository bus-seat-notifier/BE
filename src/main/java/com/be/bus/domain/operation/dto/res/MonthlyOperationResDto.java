package com.be.bus.domain.operation.dto.res;

import com.be.bus.domain.operation.entity.Operation;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder(access = AccessLevel.PRIVATE)
public record MonthlyOperationResDto(
    Map<LocalDate, List<OperationDto>> operationMap
) {
    static public MonthlyOperationResDto of(List<Operation> operationList) {
        Map<LocalDate, List<OperationDto>> operationMap = new HashMap<>();

        operationList.forEach(operation -> {
            LocalDate key = operation.getDepartureDtm().toLocalDate();
            OperationDto item = OperationDto.of(
                    operation.getDepartureDtm().toLocalTime(),
                    operation.getBusCompany(),
                    operation.getBusType(),
                    operation.getDuration(),
                    operation.getPrice());

            // 해당 날짜 키 미 존재
            if (!operationMap.containsKey(key)) {
                operationMap.put(key, new ArrayList<>());            
            }
            operationMap.get(key).add(item);

        });
        return MonthlyOperationResDto.builder()
                .operationMap(operationMap)
                .build();
    }
}

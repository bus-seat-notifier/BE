package com.be.bus.domain.alertRegisterInfo.helper;

import com.be.bus.domain.alertRegisterInfo.enums.SeatAlertType;
import com.be.bus.domain.alertRegisterInfo.error.AlertRegisterInfoErrorCode;
import com.be.bus.domain.alertRegisterInfo.repository.AlertRegisterInfoRepository;
import com.be.bus.domain.alertRegisterSeatInfo.entity.AlertRegisterInfo;
import com.be.bus.domain.operation.entity.Operation;
import com.be.bus.domain.user.entity.User;
import com.be.bus.global.error.exception.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class AlertRegisterInfoHelper {
    private final AlertRegisterInfoRepository alertRegisterInfoRepository;

    public void save(User user, Operation operation) {
        boolean isExist = alertRegisterInfoRepository.existsByUserAndOperation(user, operation);
        if (isExist) {
            throw new ConflictException(AlertRegisterInfoErrorCode.ALREADY_EXIST_INFO);
        }

        // TODO : SeatAlertType을 변경할 수 있게 하기
        alertRegisterInfoRepository.save(
                AlertRegisterInfo.create(user, operation, SeatAlertType.ALL_SEAT, null)
        );
    }

    public List<AlertRegisterInfo> getAllAlertRegisterInfo() {
        return alertRegisterInfoRepository.findAll();
    }

}

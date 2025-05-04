package com.be.bus.domain.alertRegisterInfo.service;

import com.be.bus.domain.alertRegisterInfo.helper.AlertRegisterInfoHelper;
import com.be.bus.domain.alertRegisterInfo.dto.req.SaveAlertRegisterInfoReqDto;
import com.be.bus.domain.operation.entity.Operation;
import com.be.bus.domain.operation.helper.OperationHelper;
import com.be.bus.domain.user.entity.User;
import com.be.bus.domain.user.helper.UserHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SaveAlertRegisterInfoService {
    private final AlertRegisterInfoHelper alertRegisterInfoHelper;
    private final OperationHelper operationHelper;
    private final UserHelper userHelper;
    public void execute(SaveAlertRegisterInfoReqDto req) {
        Operation operation = operationHelper.findByIdOrElseThrow(req.operationId());
        User user = userHelper.findByIdOrElseThrow(req.userId());
        // TODO : user = req.getUserId();
        alertRegisterInfoHelper.save(user, operation);
    }
}

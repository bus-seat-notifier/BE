package com.be.bus.domain.alertRegisterInfo.repository;

import com.be.bus.domain.alertRegisterSeatInfo.entity.AlertRegisterInfo;
import com.be.bus.domain.operation.entity.Operation;
import com.be.bus.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRegisterInfoRepository extends JpaRepository<AlertRegisterInfo, Long> {
    boolean existsByUserAndOperation(User user, Operation operation);
}

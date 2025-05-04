package com.be.bus.domain.alertRegisterInfo.repository;

import com.be.bus.domain.alertRegisterSeatInfo.entity.AlertRegisterInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRegisterInfoRepository extends JpaRepository<AlertRegisterInfo, Long> {
}

package com.be.bus.domain.alertRegisterInfo.entity;

import com.be.bus.domain.alertRegisterSeatInfo.entity.AlertRegisterInfo;
import com.be.bus.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "alert_register_seat_info")
public class AlertRegisterSeatInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "alert_register_info_id")
    private AlertRegisterInfo alertRegisterInfo;

    @Column(name = "seat_number", nullable = false)
    private Long seatNumber;

    public static AlertRegisterSeatInfo create(AlertRegisterInfo alertRegisterInfo, Long seatNumber) {
        return AlertRegisterSeatInfo.builder()
                .alertRegisterInfo(alertRegisterInfo)
                .seatNumber(seatNumber)
                .build();
    }
}

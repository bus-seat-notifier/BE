package com.be.bus.domain.alert.entity;

import com.be.bus.domain.alert.entity.AlertRegisterInfo;
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

    @Column(name = "seat_type", nullable = false)
    private String seatType;

    public static AlertRegisterSeatInfo create(AlertRegisterInfo alertRegisterInfo, String seatType) {
        return AlertRegisterSeatInfo.builder()
                .alertRegisterInfo(alertRegisterInfo)
                .seatType(seatType)
                .build();
    }
}

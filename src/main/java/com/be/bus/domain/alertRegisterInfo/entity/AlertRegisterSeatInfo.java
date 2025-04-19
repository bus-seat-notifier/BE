package com.be.bus.domain.alertRegisterInfo.entity;

import com.be.bus.domain.alertRegisterSeatInfo.entity.AlertRegisterInfo;
import com.be.bus.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "alert_register_seat_info")
public class AlertRegisterSeatInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_number")
    private String seatNumber;

    @ManyToOne
    @JoinColumn(name = "alert_register_info_id")
    private AlertRegisterInfo alertRegisterInfo;
}

package com.be.bus.domain.alertRegisterInfo.entity;

import com.be.bus.domain.alertRegisterSeatInfo.entity.AlertRegisterInfo;
import com.be.bus.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "alert_register_seat_info")
@Getter
@Setter
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

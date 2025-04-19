package com.be.bus.domain.alertRegisterSeatInfo.entity;

import com.be.bus.domain.operation.entity.Operation;
import com.be.bus.domain.user.entity.User;
import com.be.bus.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "alert_register_info")
@Getter
@Setter
public class AlertRegisterInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_alert_dtm")
    private LocalDateTime lastAlertDateTime;

    @Column(name = "seat_alert_type")
    private String seatAlertType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "operation_id")
    private Operation operation;
}

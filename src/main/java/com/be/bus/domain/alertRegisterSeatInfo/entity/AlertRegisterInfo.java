package com.be.bus.domain.alertRegisterSeatInfo.entity;

import com.be.bus.domain.alertRegisterInfo.enums.SeatAlertType;
import com.be.bus.domain.operation.entity.Operation;
import com.be.bus.domain.user.entity.User;
import com.be.bus.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "alert_register_info")
public class AlertRegisterInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "operation_id")
    private Operation operation;

    @Column(name = "last_alert_dtm")
    private LocalDateTime lastAlertDtm;

    @Column(name = "seat_alert_type", nullable = false)
    @Enumerated(EnumType.STRING) // 문자열로 저장 (ex: "WINDOW")
    private SeatAlertType seatAlertType;


    public static AlertRegisterInfo create(User user, Operation operation, SeatAlertType seatAlertType, LocalDateTime lastAlertDtm) {
        return AlertRegisterInfo.builder()
                .user(user)
                .operation(operation)
                .seatAlertType(seatAlertType)
                .lastAlertDtm(lastAlertDtm)
                .build();
    }

}
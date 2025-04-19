package com.be.bus.domain.alert.entity;

import com.be.bus.domain.user.entity.User;
import com.be.bus.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "route_id", nullable = false)
    private Long routeId;

    @Column(name = "departure_dtm", nullable = false)
    private String departureDateTime;

    public static AlertRegisterInfo create(User user, Long routeId, String departureDateTime) {
        return AlertRegisterInfo.builder()
                .user(user)
                .routeId(routeId)
                .departureDateTime(departureDateTime)
                .build();
    }
}
package com.be.bus.domain.operation.entity;

import com.be.bus.domain.route.entity.Route;
import com.be.bus.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "operation")
public class Operation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "departure_dtm")
    private LocalDateTime departureDateTime;

    @Column(name = "bus_company")
    private String busCompany; // ✅ 운수사명 (ex. 경원여객)

    @Column(name = "bus_type")
    private String busType; // ✅ 버스등급 (ex. 프리미엄, 우등)

    @Column(name = "duration")
    private String duration; // ✅ 소요시간 (ex. 4:20)

    @Column(name = "price")
    private Integer price; // ✅ 성인요금

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Route route;

    public static Operation create(LocalDateTime departureDateTime, String busCompany, String busType, String duration, Integer price, Route route) {
        return Operation.builder()
                .departureDateTime(departureDateTime)
                .busCompany(busCompany)
                .busType(busType)
                .duration(duration)
                .price(price)
                .route(route)
                .build();
    }
}

package com.be.bus.domain.operation.entity;

import com.be.bus.domain.route.entity.Route;
import com.be.bus.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "operation")
@Getter
@Setter
public class Operation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "departure_dtm")
    private LocalDateTime departureDateTime;

    @Column(name = "bus_company")
    private String busCompany;

    @Column(name = "bus_type")
    private String busType;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;
}

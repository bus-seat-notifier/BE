package com.be.bus.domain.route.entity;

import com.be.bus.domain.terminal.entity.Terminal;
import com.be.bus.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "route")
public class Route extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "departure_terminal_id")
    private Terminal departureTerminal;

    @ManyToOne
    @JoinColumn(name = "arrival_terminal_id")
    private Terminal arrivalTerminal;
}

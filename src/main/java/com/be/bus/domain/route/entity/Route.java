package com.be.bus.domain.route.entity;

import com.be.bus.domain.terminal.entity.Terminal;
import com.be.bus.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "route")
@Getter
@Setter
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

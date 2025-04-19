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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_terminal_id")
    private Terminal departureTerminal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrival_terminal_id")
    private Terminal arrivalTerminal;

    private String name;

    public static Route create(Terminal departureTerminal, Terminal arrivalTerminal) {
        String routeName = departureTerminal.getName() + "-" + arrivalTerminal.getName();
        return Route.builder()
                .departureTerminal(departureTerminal)
                .arrivalTerminal(arrivalTerminal)
                .name(routeName)
                .build();
    }
}

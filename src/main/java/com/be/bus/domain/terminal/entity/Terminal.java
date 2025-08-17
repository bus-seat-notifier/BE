package com.be.bus.domain.terminal.entity;

import com.be.bus.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "terminal")
public class Terminal extends BaseTimeEntity {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)

    private String name;

    @Column(name = "area_cd", nullable = false)
    private String areaCode;

    @Column(name = "departure_yn", nullable = false)
    private String departureYn;

    public static Terminal create(String id, String name, String areaCode, String departureYn) {
        return Terminal.builder()
                .id(id)
                .name(name)
                .areaCode(areaCode)
                .departureYn(departureYn)
                .build();
    }
}

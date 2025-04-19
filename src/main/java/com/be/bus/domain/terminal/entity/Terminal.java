package com.be.bus.domain.terminal.entity;

import com.be.bus.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "terminal")
public class Terminal extends BaseTimeEntity {

    @Id
    private String id;

    private String name;

    @Column(name = "area_cd")
    private String areaCode;

    public static Terminal create(String id, String name, String areaCode) {
        return Terminal.builder()
                .id(id)
                .name(name)
                .areaCode(areaCode)
                .build();
    }
}

package com.be.bus.domain.terminal.entity;

import com.be.bus.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "terminal")
@Getter
@Setter
public class Terminal extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "area_cd")
    private String areaCd;
}

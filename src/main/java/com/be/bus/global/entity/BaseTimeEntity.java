package com.be.bus.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    // 생성일시
    @CreatedDate
    @Column(name = "create_dtm", updatable = false)
    private LocalDateTime createDtm;

    // 수정일시
    @LastModifiedDate
    @Column(name = "update_dtm")
    private LocalDateTime updateDtm;

    // 삭제일시 (Soft delete용)
    @Column(name = "delete_dtm")
    private LocalDateTime deleteDtm;

    // 삭제 여부 (Y/N)
    @Column(name = "delete_yn", length = 1, nullable = false)
    private String deleteYn = "N";
}

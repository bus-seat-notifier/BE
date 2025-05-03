package com.be.bus.domain.alertRegisterInfo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlertRegisterSeatInfo is a Querydsl query type for AlertRegisterSeatInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlertRegisterSeatInfo extends EntityPathBase<AlertRegisterSeatInfo> {

    private static final long serialVersionUID = -1390068890L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlertRegisterSeatInfo alertRegisterSeatInfo = new QAlertRegisterSeatInfo("alertRegisterSeatInfo");

    public final com.be.bus.global.entity.QBaseTimeEntity _super = new com.be.bus.global.entity.QBaseTimeEntity(this);

    public final com.be.bus.domain.alertRegisterSeatInfo.entity.QAlertRegisterInfo alertRegisterInfo;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final StringPath deleteYn = _super.deleteYn;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> seatNumber = createNumber("seatNumber", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QAlertRegisterSeatInfo(String variable) {
        this(AlertRegisterSeatInfo.class, forVariable(variable), INITS);
    }

    public QAlertRegisterSeatInfo(Path<? extends AlertRegisterSeatInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlertRegisterSeatInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlertRegisterSeatInfo(PathMetadata metadata, PathInits inits) {
        this(AlertRegisterSeatInfo.class, metadata, inits);
    }

    public QAlertRegisterSeatInfo(Class<? extends AlertRegisterSeatInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.alertRegisterInfo = inits.isInitialized("alertRegisterInfo") ? new com.be.bus.domain.alertRegisterSeatInfo.entity.QAlertRegisterInfo(forProperty("alertRegisterInfo"), inits.get("alertRegisterInfo")) : null;
    }

}


package com.be.bus.domain.alert.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlertRegisterInfo is a Querydsl query type for AlertRegisterInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlertRegisterInfo extends EntityPathBase<AlertRegisterInfo> {

    private static final long serialVersionUID = -406387406L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlertRegisterInfo alertRegisterInfo = new QAlertRegisterInfo("alertRegisterInfo");

    public final com.be.bus.global.entity.QBaseTimeEntity _super = new com.be.bus.global.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final StringPath deleteYn = _super.deleteYn;

    public final StringPath departureDateTime = createString("departureDateTime");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> routeId = createNumber("routeId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.be.bus.domain.user.entity.QUser user;

    public QAlertRegisterInfo(String variable) {
        this(AlertRegisterInfo.class, forVariable(variable), INITS);
    }

    public QAlertRegisterInfo(Path<? extends AlertRegisterInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlertRegisterInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlertRegisterInfo(PathMetadata metadata, PathInits inits) {
        this(AlertRegisterInfo.class, metadata, inits);
    }

    public QAlertRegisterInfo(Class<? extends AlertRegisterInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.be.bus.domain.user.entity.QUser(forProperty("user")) : null;
    }

}


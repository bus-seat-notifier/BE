package com.be.bus.domain.operation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOperation is a Querydsl query type for Operation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOperation extends EntityPathBase<Operation> {

    private static final long serialVersionUID = -1856107903L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOperation operation = new QOperation("operation");

    public final com.be.bus.global.entity.QBaseTimeEntity _super = new com.be.bus.global.entity.QBaseTimeEntity(this);

    public final StringPath busCompany = createString("busCompany");

    public final StringPath busType = createString("busType");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final StringPath deleteYn = _super.deleteYn;

    public final DateTimePath<java.time.LocalDateTime> departureDateTime = createDateTime("departureDateTime", java.time.LocalDateTime.class);

    public final StringPath duration = createString("duration");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.be.bus.domain.route.entity.QRoute route;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QOperation(String variable) {
        this(Operation.class, forVariable(variable), INITS);
    }

    public QOperation(Path<? extends Operation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOperation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOperation(PathMetadata metadata, PathInits inits) {
        this(Operation.class, metadata, inits);
    }

    public QOperation(Class<? extends Operation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.route = inits.isInitialized("route") ? new com.be.bus.domain.route.entity.QRoute(forProperty("route"), inits.get("route")) : null;
    }

}


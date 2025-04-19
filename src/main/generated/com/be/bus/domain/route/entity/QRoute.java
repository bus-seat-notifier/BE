package com.be.bus.domain.route.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoute is a Querydsl query type for Route
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoute extends EntityPathBase<Route> {

    private static final long serialVersionUID = -127777343L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoute route = new QRoute("route");

    public final com.be.bus.global.entity.QBaseTimeEntity _super = new com.be.bus.global.entity.QBaseTimeEntity(this);

    public final com.be.bus.domain.terminal.entity.QTerminal arrivalTerminal;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final StringPath deleteYn = _super.deleteYn;

    public final com.be.bus.domain.terminal.entity.QTerminal departureTerminal;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QRoute(String variable) {
        this(Route.class, forVariable(variable), INITS);
    }

    public QRoute(Path<? extends Route> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoute(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoute(PathMetadata metadata, PathInits inits) {
        this(Route.class, metadata, inits);
    }

    public QRoute(Class<? extends Route> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.arrivalTerminal = inits.isInitialized("arrivalTerminal") ? new com.be.bus.domain.terminal.entity.QTerminal(forProperty("arrivalTerminal")) : null;
        this.departureTerminal = inits.isInitialized("departureTerminal") ? new com.be.bus.domain.terminal.entity.QTerminal(forProperty("departureTerminal")) : null;
    }

}


package com.be.bus.domain.terminal.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTerminal is a Querydsl query type for Terminal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTerminal extends EntityPathBase<Terminal> {

    private static final long serialVersionUID = -839155661L;

    public static final QTerminal terminal = new QTerminal("terminal");

    public final com.be.bus.global.entity.QBaseTimeEntity _super = new com.be.bus.global.entity.QBaseTimeEntity(this);

    public final StringPath areaCode = createString("areaCode");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final StringPath deleteYn = _super.deleteYn;

    public final StringPath id = createString("id");

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QTerminal(String variable) {
        super(Terminal.class, forVariable(variable));
    }

    public QTerminal(Path<? extends Terminal> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTerminal(PathMetadata metadata) {
        super(Terminal.class, metadata);
    }

}


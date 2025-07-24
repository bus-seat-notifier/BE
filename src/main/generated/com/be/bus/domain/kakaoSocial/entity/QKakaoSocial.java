package com.be.bus.domain.kakaoSocial.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QKakaoSocial is a Querydsl query type for KakaoSocial
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKakaoSocial extends EntityPathBase<KakaoSocial> {

    private static final long serialVersionUID = 481366497L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QKakaoSocial kakaoSocial = new QKakaoSocial("kakaoSocial");

    public final com.be.bus.global.entity.QBaseTimeEntity _super = new com.be.bus.global.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDtm = _super.createDtm;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDtm = _super.deleteDtm;

    //inherited
    public final StringPath deleteYn = _super.deleteYn;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> kakaoMemberId = createNumber("kakaoMemberId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDtm = _super.updateDtm;

    public final com.be.bus.domain.user.entity.QUser user;

    public QKakaoSocial(String variable) {
        this(KakaoSocial.class, forVariable(variable), INITS);
    }

    public QKakaoSocial(Path<? extends KakaoSocial> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QKakaoSocial(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QKakaoSocial(PathMetadata metadata, PathInits inits) {
        this(KakaoSocial.class, metadata, inits);
    }

    public QKakaoSocial(Class<? extends KakaoSocial> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.be.bus.domain.user.entity.QUser(forProperty("user")) : null;
    }

}


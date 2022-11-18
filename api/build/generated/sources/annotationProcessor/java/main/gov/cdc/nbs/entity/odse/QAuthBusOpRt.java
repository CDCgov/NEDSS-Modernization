package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAuthBusOpRt is a Querydsl query type for AuthBusOpRt
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuthBusOpRt extends EntityPathBase<AuthBusOpRt> {

    private static final long serialVersionUID = 2033861892L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAuthBusOpRt authBusOpRt = new QAuthBusOpRt("authBusOpRt");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final QAuthBusObjRt authBusObjRtUid;

    public final QAuthBusOpType authBusOpTypeUid;

    public final ComparablePath<Character> busOpGuestRt = createComparable("busOpGuestRt", Character.class);

    public final ComparablePath<Character> busOpUserRt = createComparable("busOpUserRt", Character.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public QAuthBusOpRt(String variable) {
        this(AuthBusOpRt.class, forVariable(variable), INITS);
    }

    public QAuthBusOpRt(Path<? extends AuthBusOpRt> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAuthBusOpRt(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAuthBusOpRt(PathMetadata metadata, PathInits inits) {
        this(AuthBusOpRt.class, metadata, inits);
    }

    public QAuthBusOpRt(Class<? extends AuthBusOpRt> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.authBusObjRtUid = inits.isInitialized("authBusObjRtUid") ? new QAuthBusObjRt(forProperty("authBusObjRtUid"), inits.get("authBusObjRtUid")) : null;
        this.authBusOpTypeUid = inits.isInitialized("authBusOpTypeUid") ? new QAuthBusOpType(forProperty("authBusOpTypeUid")) : null;
    }

}


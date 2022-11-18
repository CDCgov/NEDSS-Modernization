package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAuthBusObjRt is a Querydsl query type for AuthBusObjRt
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuthBusObjRt extends EntityPathBase<AuthBusObjRt> {

    private static final long serialVersionUID = -1375185736L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAuthBusObjRt authBusObjRt = new QAuthBusObjRt("authBusObjRt");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final QAuthBusObjType authBusObjTypeUid;

    public final QAuthPermSet authPermSetUid;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public QAuthBusObjRt(String variable) {
        this(AuthBusObjRt.class, forVariable(variable), INITS);
    }

    public QAuthBusObjRt(Path<? extends AuthBusObjRt> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAuthBusObjRt(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAuthBusObjRt(PathMetadata metadata, PathInits inits) {
        this(AuthBusObjRt.class, metadata, inits);
    }

    public QAuthBusObjRt(Class<? extends AuthBusObjRt> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.authBusObjTypeUid = inits.isInitialized("authBusObjTypeUid") ? new QAuthBusObjType(forProperty("authBusObjTypeUid")) : null;
        this.authPermSetUid = inits.isInitialized("authPermSetUid") ? new QAuthPermSet(forProperty("authPermSetUid")) : null;
    }

}


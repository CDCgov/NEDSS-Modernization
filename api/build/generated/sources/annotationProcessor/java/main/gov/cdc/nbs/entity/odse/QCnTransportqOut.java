package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCnTransportqOut is a Querydsl query type for CnTransportqOut
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCnTransportqOut extends EntityPathBase<CnTransportqOut> {

    private static final long serialVersionUID = -610411068L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCnTransportqOut cnTransportqOut = new QCnTransportqOut("cnTransportqOut");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath messagePayload = createString("messagePayload");

    public final StringPath notificationLocalId = createString("notificationLocalId");

    public final QNotification notificationUid;

    public final StringPath publicHealthCaseLocalId = createString("publicHealthCaseLocalId");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath reportStatusCd = createString("reportStatusCd");

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QCnTransportqOut(String variable) {
        this(CnTransportqOut.class, forVariable(variable), INITS);
    }

    public QCnTransportqOut(Path<? extends CnTransportqOut> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCnTransportqOut(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCnTransportqOut(PathMetadata metadata, PathInits inits) {
        this(CnTransportqOut.class, metadata, inits);
    }

    public QCnTransportqOut(Class<? extends CnTransportqOut> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.notificationUid = inits.isInitialized("notificationUid") ? new QNotification(forProperty("notificationUid"), inits.get("notificationUid")) : null;
    }

}


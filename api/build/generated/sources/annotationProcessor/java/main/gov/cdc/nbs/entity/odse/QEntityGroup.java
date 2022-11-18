package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEntityGroup is a Querydsl query type for EntityGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEntityGroup extends EntityPathBase<EntityGroup> {

    private static final long serialVersionUID = -535103099L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEntityGroup entityGroup = new QEntityGroup("entityGroup");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath cd = createString("cd");

    public final StringPath cdDescTxt = createString("cdDescTxt");

    public final StringPath description = createString("description");

    public final StringPath durationAmt = createString("durationAmt");

    public final StringPath durationUnitCd = createString("durationUnitCd");

    public final DateTimePath<java.time.Instant> fromTime = createDateTime("fromTime", java.time.Instant.class);

    public final NumberPath<Short> groupCnt = createNumber("groupCnt", Short.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final QNBSEntity NBSEntity;

    public final StringPath nm = createString("nm");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> toTime = createDateTime("toTime", java.time.Instant.class);

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QEntityGroup(String variable) {
        this(EntityGroup.class, forVariable(variable), INITS);
    }

    public QEntityGroup(Path<? extends EntityGroup> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEntityGroup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEntityGroup(PathMetadata metadata, PathInits inits) {
        this(EntityGroup.class, metadata, inits);
    }

    public QEntityGroup(Class<? extends EntityGroup> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.NBSEntity = inits.isInitialized("NBSEntity") ? new QNBSEntity(forProperty("NBSEntity")) : null;
    }

}


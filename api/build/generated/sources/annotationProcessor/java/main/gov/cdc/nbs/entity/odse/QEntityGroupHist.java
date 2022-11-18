package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEntityGroupHist is a Querydsl query type for EntityGroupHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEntityGroupHist extends EntityPathBase<EntityGroupHist> {

    private static final long serialVersionUID = -9764281L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEntityGroupHist entityGroupHist = new QEntityGroupHist("entityGroupHist");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath cd = createString("cd");

    public final StringPath cdDescTxt = createString("cdDescTxt");

    public final StringPath description = createString("description");

    public final StringPath durationAmt = createString("durationAmt");

    public final StringPath durationUnitCd = createString("durationUnitCd");

    public final QEntityGroup entityGroupUid;

    public final DateTimePath<java.time.Instant> fromTime = createDateTime("fromTime", java.time.Instant.class);

    public final NumberPath<Short> groupCnt = createNumber("groupCnt", Short.class);

    public final QEntityGroupHistId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final StringPath nm = createString("nm");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> toTime = createDateTime("toTime", java.time.Instant.class);

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public QEntityGroupHist(String variable) {
        this(EntityGroupHist.class, forVariable(variable), INITS);
    }

    public QEntityGroupHist(Path<? extends EntityGroupHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEntityGroupHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEntityGroupHist(PathMetadata metadata, PathInits inits) {
        this(EntityGroupHist.class, metadata, inits);
    }

    public QEntityGroupHist(Class<? extends EntityGroupHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.entityGroupUid = inits.isInitialized("entityGroupUid") ? new QEntityGroup(forProperty("entityGroupUid"), inits.get("entityGroupUid")) : null;
        this.id = inits.isInitialized("id") ? new QEntityGroupHistId(forProperty("id")) : null;
    }

}


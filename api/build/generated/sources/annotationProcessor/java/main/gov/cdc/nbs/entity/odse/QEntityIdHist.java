package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEntityIdHist is a Querydsl query type for EntityIdHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEntityIdHist extends EntityPathBase<EntityIdHist> {

    private static final long serialVersionUID = 634828951L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEntityIdHist entityIdHist = new QEntityIdHist("entityIdHist");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final DateTimePath<java.time.Instant> asOfDate = createDateTime("asOfDate", java.time.Instant.class);

    public final StringPath assigningAuthorityCd = createString("assigningAuthorityCd");

    public final StringPath assigningAuthorityDescTxt = createString("assigningAuthorityDescTxt");

    public final StringPath assigningAuthorityIdType = createString("assigningAuthorityIdType");

    public final StringPath durationAmt = createString("durationAmt");

    public final StringPath durationUnitCd = createString("durationUnitCd");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final QEntityId entity;

    public final QEntityIdHistId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath rootExtensionTxt = createString("rootExtensionTxt");

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath typeCd = createString("typeCd");

    public final StringPath typeDescTxt = createString("typeDescTxt");

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public final DateTimePath<java.time.Instant> validFromTime = createDateTime("validFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> validToTime = createDateTime("validToTime", java.time.Instant.class);

    public QEntityIdHist(String variable) {
        this(EntityIdHist.class, forVariable(variable), INITS);
    }

    public QEntityIdHist(Path<? extends EntityIdHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEntityIdHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEntityIdHist(PathMetadata metadata, PathInits inits) {
        this(EntityIdHist.class, metadata, inits);
    }

    public QEntityIdHist(Class<? extends EntityIdHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.entity = inits.isInitialized("entity") ? new QEntityId(forProperty("entity"), inits.get("entity")) : null;
        this.id = inits.isInitialized("id") ? new QEntityIdHistId(forProperty("id")) : null;
    }

}


package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEntityId is a Querydsl query type for EntityId
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEntityId extends EntityPathBase<EntityId> {

    private static final long serialVersionUID = -16885803L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEntityId entityId = new QEntityId("entityId");

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

    public final QEntityIdId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final QNBSEntity NBSEntityUid;

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath rootExtensionTxt = createString("rootExtensionTxt");

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final EnumPath<gov.cdc.nbs.entity.enums.IdentificationType> typeCd = createEnum("typeCd", gov.cdc.nbs.entity.enums.IdentificationType.class);

    public final StringPath typeDescTxt = createString("typeDescTxt");

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public final DateTimePath<java.time.Instant> validFromTime = createDateTime("validFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> validToTime = createDateTime("validToTime", java.time.Instant.class);

    public QEntityId(String variable) {
        this(EntityId.class, forVariable(variable), INITS);
    }

    public QEntityId(Path<? extends EntityId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEntityId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEntityId(PathMetadata metadata, PathInits inits) {
        this(EntityId.class, metadata, inits);
    }

    public QEntityId(Class<? extends EntityId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QEntityIdId(forProperty("id")) : null;
        this.NBSEntityUid = inits.isInitialized("NBSEntityUid") ? new QNBSEntity(forProperty("NBSEntityUid")) : null;
    }

}


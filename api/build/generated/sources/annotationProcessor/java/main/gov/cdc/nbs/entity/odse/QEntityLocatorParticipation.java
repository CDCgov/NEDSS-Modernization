package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEntityLocatorParticipation is a Querydsl query type for EntityLocatorParticipation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEntityLocatorParticipation extends EntityPathBase<EntityLocatorParticipation> {

    private static final long serialVersionUID = 1243174021L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEntityLocatorParticipation entityLocatorParticipation = new QEntityLocatorParticipation("entityLocatorParticipation");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final DateTimePath<java.time.Instant> asOfDate = createDateTime("asOfDate", java.time.Instant.class);

    public final StringPath cd = createString("cd");

    public final StringPath cdDescTxt = createString("cdDescTxt");

    public final StringPath classCd = createString("classCd");

    public final StringPath durationAmt = createString("durationAmt");

    public final StringPath durationUnitCd = createString("durationUnitCd");

    public final DateTimePath<java.time.Instant> fromTime = createDateTime("fromTime", java.time.Instant.class);

    public final QEntityLocatorParticipationId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final SimplePath<EntityLocatorParticipation.Locator> locator = createSimple("locator", EntityLocatorParticipation.Locator.class);

    public final StringPath locatorDescTxt = createString("locatorDescTxt");

    public final QNBSEntity nbsEntity;

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> toTime = createDateTime("toTime", java.time.Instant.class);

    public final StringPath useCd = createString("useCd");

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public final StringPath validTimeTxt = createString("validTimeTxt");

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QEntityLocatorParticipation(String variable) {
        this(EntityLocatorParticipation.class, forVariable(variable), INITS);
    }

    public QEntityLocatorParticipation(Path<? extends EntityLocatorParticipation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEntityLocatorParticipation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEntityLocatorParticipation(PathMetadata metadata, PathInits inits) {
        this(EntityLocatorParticipation.class, metadata, inits);
    }

    public QEntityLocatorParticipation(Class<? extends EntityLocatorParticipation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QEntityLocatorParticipationId(forProperty("id")) : null;
        this.nbsEntity = inits.isInitialized("nbsEntity") ? new QNBSEntity(forProperty("nbsEntity")) : null;
    }

}


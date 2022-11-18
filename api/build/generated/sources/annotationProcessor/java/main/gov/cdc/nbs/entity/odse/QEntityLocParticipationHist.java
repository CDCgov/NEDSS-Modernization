package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEntityLocParticipationHist is a Querydsl query type for EntityLocParticipationHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEntityLocParticipationHist extends EntityPathBase<EntityLocParticipationHist> {

    private static final long serialVersionUID = -1844859683L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEntityLocParticipationHist entityLocParticipationHist = new QEntityLocParticipationHist("entityLocParticipationHist");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final DateTimePath<java.time.Instant> asOfDate = createDateTime("asOfDate", java.time.Instant.class);

    public final StringPath cd = createString("cd");

    public final StringPath cdDescTxt = createString("cdDescTxt");

    public final StringPath classCd = createString("classCd");

    public final StringPath durationAmt = createString("durationAmt");

    public final StringPath durationUnitCd = createString("durationUnitCd");

    public final QEntityLocatorParticipation entityLocatorParticipation;

    public final DateTimePath<java.time.Instant> fromTime = createDateTime("fromTime", java.time.Instant.class);

    public final QEntityLocParticipationHistId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath locatorDescTxt = createString("locatorDescTxt");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> toTime = createDateTime("toTime", java.time.Instant.class);

    public final StringPath useCd = createString("useCd");

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public final StringPath validTimeTxt = createString("validTimeTxt");

    public QEntityLocParticipationHist(String variable) {
        this(EntityLocParticipationHist.class, forVariable(variable), INITS);
    }

    public QEntityLocParticipationHist(Path<? extends EntityLocParticipationHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEntityLocParticipationHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEntityLocParticipationHist(PathMetadata metadata, PathInits inits) {
        this(EntityLocParticipationHist.class, metadata, inits);
    }

    public QEntityLocParticipationHist(Class<? extends EntityLocParticipationHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.entityLocatorParticipation = inits.isInitialized("entityLocatorParticipation") ? new QEntityLocatorParticipation(forProperty("entityLocatorParticipation"), inits.get("entityLocatorParticipation")) : null;
        this.id = inits.isInitialized("id") ? new QEntityLocParticipationHistId(forProperty("id")) : null;
    }

}


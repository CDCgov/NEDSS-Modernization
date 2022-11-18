package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QActLocatorParticipationHist is a Querydsl query type for ActLocatorParticipationHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QActLocatorParticipationHist extends EntityPathBase<ActLocatorParticipationHist> {

    private static final long serialVersionUID = 46051304L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QActLocatorParticipationHist actLocatorParticipationHist = new QActLocatorParticipationHist("actLocatorParticipationHist");

    public final QActLocatorParticipation actLocatorParticipation;

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath durationAmt = createString("durationAmt");

    public final StringPath durationUnitCd = createString("durationUnitCd");

    public final DateTimePath<java.time.Instant> fromTime = createDateTime("fromTime", java.time.Instant.class);

    public final QActLocatorParticipationHistId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> toTime = createDateTime("toTime", java.time.Instant.class);

    public final StringPath typeCd = createString("typeCd");

    public final StringPath typeDescTxt = createString("typeDescTxt");

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public QActLocatorParticipationHist(String variable) {
        this(ActLocatorParticipationHist.class, forVariable(variable), INITS);
    }

    public QActLocatorParticipationHist(Path<? extends ActLocatorParticipationHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QActLocatorParticipationHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QActLocatorParticipationHist(PathMetadata metadata, PathInits inits) {
        this(ActLocatorParticipationHist.class, metadata, inits);
    }

    public QActLocatorParticipationHist(Class<? extends ActLocatorParticipationHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.actLocatorParticipation = inits.isInitialized("actLocatorParticipation") ? new QActLocatorParticipation(forProperty("actLocatorParticipation"), inits.get("actLocatorParticipation")) : null;
        this.id = inits.isInitialized("id") ? new QActLocatorParticipationHistId(forProperty("id")) : null;
    }

}


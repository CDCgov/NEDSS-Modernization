package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QActLocatorParticipation is a Querydsl query type for ActLocatorParticipation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QActLocatorParticipation extends EntityPathBase<ActLocatorParticipation> {

    private static final long serialVersionUID = -1461674074L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QActLocatorParticipation actLocatorParticipation = new QActLocatorParticipation("actLocatorParticipation");

    public final QAct actUid;

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath durationAmt = createString("durationAmt");

    public final StringPath durationUnitCd = createString("durationUnitCd");

    public final DateTimePath<java.time.Instant> fromTime = createDateTime("fromTime", java.time.Instant.class);

    public final QActLocatorParticipationId id;

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

    public QActLocatorParticipation(String variable) {
        this(ActLocatorParticipation.class, forVariable(variable), INITS);
    }

    public QActLocatorParticipation(Path<? extends ActLocatorParticipation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QActLocatorParticipation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QActLocatorParticipation(PathMetadata metadata, PathInits inits) {
        this(ActLocatorParticipation.class, metadata, inits);
    }

    public QActLocatorParticipation(Class<? extends ActLocatorParticipation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.actUid = inits.isInitialized("actUid") ? new QAct(forProperty("actUid")) : null;
        this.id = inits.isInitialized("id") ? new QActLocatorParticipationId(forProperty("id")) : null;
    }

}


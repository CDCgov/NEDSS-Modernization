package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QParticipationHist is a Querydsl query type for ParticipationHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QParticipationHist extends EntityPathBase<ParticipationHist> {

    private static final long serialVersionUID = 371521132L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QParticipationHist participationHist = new QParticipationHist("participationHist");

    public final StringPath actClassCd = createString("actClassCd");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath awarenessCd = createString("awarenessCd");

    public final StringPath awarenessDescTxt = createString("awarenessDescTxt");

    public final StringPath cd = createString("cd");

    public final StringPath durationAmt = createString("durationAmt");

    public final StringPath durationUnitCd = createString("durationUnitCd");

    public final DateTimePath<java.time.Instant> fromTime = createDateTime("fromTime", java.time.Instant.class);

    public final QParticipationHistId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final NumberPath<Long> roleSeq = createNumber("roleSeq", Long.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath subjectClassCd = createString("subjectClassCd");

    public final DateTimePath<java.time.Instant> toTime = createDateTime("toTime", java.time.Instant.class);

    public final StringPath typeDescTxt = createString("typeDescTxt");

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public QParticipationHist(String variable) {
        this(ParticipationHist.class, forVariable(variable), INITS);
    }

    public QParticipationHist(Path<? extends ParticipationHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QParticipationHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QParticipationHist(PathMetadata metadata, PathInits inits) {
        this(ParticipationHist.class, metadata, inits);
    }

    public QParticipationHist(Class<? extends ParticipationHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QParticipationHistId(forProperty("id")) : null;
    }

}


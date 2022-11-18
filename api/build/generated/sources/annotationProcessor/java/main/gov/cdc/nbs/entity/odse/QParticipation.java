package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QParticipation is a Querydsl query type for Participation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QParticipation extends EntityPathBase<Participation> {

    private static final long serialVersionUID = 743884842L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QParticipation participation = new QParticipation("participation");

    public final StringPath actClassCd = createString("actClassCd");

    public final QAct actUid;

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath awarenessCd = createString("awarenessCd");

    public final StringPath awarenessDescTxt = createString("awarenessDescTxt");

    public final StringPath cd = createString("cd");

    public final StringPath durationAmt = createString("durationAmt");

    public final StringPath durationUnitCd = createString("durationUnitCd");

    public final DateTimePath<java.time.Instant> fromTime = createDateTime("fromTime", java.time.Instant.class);

    public final QParticipationId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final EnumPath<gov.cdc.nbs.entity.enums.RecordStatus> recordStatusCd = createEnum("recordStatusCd", gov.cdc.nbs.entity.enums.RecordStatus.class);

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final QRole role;

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath subjectClassCd = createString("subjectClassCd");

    public final DateTimePath<java.time.Instant> toTime = createDateTime("toTime", java.time.Instant.class);

    public final StringPath typeDescTxt = createString("typeDescTxt");

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public QParticipation(String variable) {
        this(Participation.class, forVariable(variable), INITS);
    }

    public QParticipation(Path<? extends Participation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QParticipation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QParticipation(PathMetadata metadata, PathInits inits) {
        this(Participation.class, metadata, inits);
    }

    public QParticipation(Class<? extends Participation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.actUid = inits.isInitialized("actUid") ? new QAct(forProperty("actUid")) : null;
        this.id = inits.isInitialized("id") ? new QParticipationId(forProperty("id")) : null;
        this.role = inits.isInitialized("role") ? new QRole(forProperty("role"), inits.get("role")) : null;
    }

}


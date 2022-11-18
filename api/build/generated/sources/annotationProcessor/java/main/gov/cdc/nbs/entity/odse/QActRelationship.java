package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QActRelationship is a Querydsl query type for ActRelationship
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QActRelationship extends EntityPathBase<ActRelationship> {

    private static final long serialVersionUID = -1469506413L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QActRelationship actRelationship = new QActRelationship("actRelationship");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath durationAmt = createString("durationAmt");

    public final StringPath durationUnitCd = createString("durationUnitCd");

    public final DateTimePath<java.time.Instant> fromTime = createDateTime("fromTime", java.time.Instant.class);

    public final QActRelationshipId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final NumberPath<Short> sequenceNbr = createNumber("sequenceNbr", Short.class);

    public final QAct sourceActUid;

    public final StringPath sourceClassCd = createString("sourceClassCd");

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final QAct targetActUid;

    public final StringPath targetClassCd = createString("targetClassCd");

    public final DateTimePath<java.time.Instant> toTime = createDateTime("toTime", java.time.Instant.class);

    public final StringPath typeDescTxt = createString("typeDescTxt");

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public QActRelationship(String variable) {
        this(ActRelationship.class, forVariable(variable), INITS);
    }

    public QActRelationship(Path<? extends ActRelationship> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QActRelationship(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QActRelationship(PathMetadata metadata, PathInits inits) {
        this(ActRelationship.class, metadata, inits);
    }

    public QActRelationship(Class<? extends ActRelationship> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QActRelationshipId(forProperty("id")) : null;
        this.sourceActUid = inits.isInitialized("sourceActUid") ? new QAct(forProperty("sourceActUid")) : null;
        this.targetActUid = inits.isInitialized("targetActUid") ? new QAct(forProperty("targetActUid")) : null;
    }

}


package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QActRelationshipHist is a Querydsl query type for ActRelationshipHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QActRelationshipHist extends EntityPathBase<ActRelationshipHist> {

    private static final long serialVersionUID = -558567851L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QActRelationshipHist actRelationshipHist = new QActRelationshipHist("actRelationshipHist");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath durationAmt = createString("durationAmt");

    public final StringPath durationUnitCd = createString("durationUnitCd");

    public final DateTimePath<java.time.Instant> fromTime = createDateTime("fromTime", java.time.Instant.class);

    public final QActRelationshipHistId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final NumberPath<Short> sequenceNbr = createNumber("sequenceNbr", Short.class);

    public final StringPath sourceClassCd = createString("sourceClassCd");

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath targetClassCd = createString("targetClassCd");

    public final DateTimePath<java.time.Instant> toTime = createDateTime("toTime", java.time.Instant.class);

    public final StringPath typeDescTxt = createString("typeDescTxt");

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public QActRelationshipHist(String variable) {
        this(ActRelationshipHist.class, forVariable(variable), INITS);
    }

    public QActRelationshipHist(Path<? extends ActRelationshipHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QActRelationshipHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QActRelationshipHist(PathMetadata metadata, PathInits inits) {
        this(ActRelationshipHist.class, metadata, inits);
    }

    public QActRelationshipHist(Class<? extends ActRelationshipHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QActRelationshipHistId(forProperty("id")) : null;
    }

}


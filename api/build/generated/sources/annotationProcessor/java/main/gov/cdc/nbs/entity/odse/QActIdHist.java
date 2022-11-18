package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QActIdHist is a Querydsl query type for ActIdHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QActIdHist extends EntityPathBase<ActIdHist> {

    private static final long serialVersionUID = -511991432L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QActIdHist actIdHist = new QActIdHist("actIdHist");

    public final QActId act;

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath assigningAuthorityCd = createString("assigningAuthorityCd");

    public final StringPath assigningAuthorityDescTxt = createString("assigningAuthorityDescTxt");

    public final StringPath durationAmt = createString("durationAmt");

    public final StringPath durationUnitCd = createString("durationUnitCd");

    public final QActIdHistId id;

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

    public QActIdHist(String variable) {
        this(ActIdHist.class, forVariable(variable), INITS);
    }

    public QActIdHist(Path<? extends ActIdHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QActIdHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QActIdHist(PathMetadata metadata, PathInits inits) {
        this(ActIdHist.class, metadata, inits);
    }

    public QActIdHist(Class<? extends ActIdHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.act = inits.isInitialized("act") ? new QActId(forProperty("act"), inits.get("act")) : null;
        this.id = inits.isInitialized("id") ? new QActIdHistId(forProperty("id")) : null;
    }

}


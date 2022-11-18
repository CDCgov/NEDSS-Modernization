package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWorkupHist is a Querydsl query type for WorkupHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWorkupHist extends EntityPathBase<WorkupHist> {

    private static final long serialVersionUID = -1787434907L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWorkupHist workupHist = new QWorkupHist("workupHist");

    public final StringPath activityDurationAmt = createString("activityDurationAmt");

    public final StringPath activityDurationUnitCd = createString("activityDurationUnitCd");

    public final DateTimePath<java.time.Instant> activityFromTime = createDateTime("activityFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> activityToTime = createDateTime("activityToTime", java.time.Instant.class);

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final DateTimePath<java.time.Instant> assignTime = createDateTime("assignTime", java.time.Instant.class);

    public final NumberPath<Long> assignUserId = createNumber("assignUserId", Long.class);

    public final StringPath cd = createString("cd");

    public final StringPath cdDescTxt = createString("cdDescTxt");

    public final StringPath confidentialityCd = createString("confidentialityCd");

    public final StringPath confidentialityDescTxt = createString("confidentialityDescTxt");

    public final StringPath diagnosisCd = createString("diagnosisCd");

    public final StringPath diagnosisDescTxt = createString("diagnosisDescTxt");

    public final StringPath dispositionCd = createString("dispositionCd");

    public final StringPath dispositionDescTxt = createString("dispositionDescTxt");

    public final DateTimePath<java.time.Instant> dispositionTime = createDateTime("dispositionTime", java.time.Instant.class);

    public final NumberPath<Long> dispositionWorkerId = createNumber("dispositionWorkerId", Long.class);

    public final StringPath effectiveDurationAmt = createString("effectiveDurationAmt");

    public final StringPath effectiveDurationUnitCd = createString("effectiveDurationUnitCd");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final StringPath exposureFrequency = createString("exposureFrequency");

    public final DateTimePath<java.time.Instant> exposureFromTime = createDateTime("exposureFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> exposureToTime = createDateTime("exposureToTime", java.time.Instant.class);

    public final QWorkupHistId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final NumberPath<Long> programJurisdictionOid = createNumber("programJurisdictionOid", Long.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final NumberPath<Short> repeatNbr = createNumber("repeatNbr", Short.class);

    public final ComparablePath<Character> sharedInd = createComparable("sharedInd", Character.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath txt = createString("txt");

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public final QWorkup workupUid;

    public QWorkupHist(String variable) {
        this(WorkupHist.class, forVariable(variable), INITS);
    }

    public QWorkupHist(Path<? extends WorkupHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWorkupHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWorkupHist(PathMetadata metadata, PathInits inits) {
        this(WorkupHist.class, metadata, inits);
    }

    public QWorkupHist(Class<? extends WorkupHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QWorkupHistId(forProperty("id")) : null;
        this.workupUid = inits.isInitialized("workupUid") ? new QWorkup(forProperty("workupUid"), inits.get("workupUid")) : null;
    }

}


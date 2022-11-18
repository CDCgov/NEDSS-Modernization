package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWorkup is a Querydsl query type for Workup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWorkup extends EntityPathBase<Workup> {

    private static final long serialVersionUID = -1369858397L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWorkup workup = new QWorkup("workup");

    public final QAct act;

    public final StringPath activityDurationAmt = createString("activityDurationAmt");

    public final StringPath activityDurationUnitCd = createString("activityDurationUnitCd");

    public final DateTimePath<java.time.Instant> activityFromTime = createDateTime("activityFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> activityToTime = createDateTime("activityToTime", java.time.Instant.class);

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final DateTimePath<java.time.Instant> assignTime = createDateTime("assignTime", java.time.Instant.class);

    public final NumberPath<Long> assignWorkerId = createNumber("assignWorkerId", Long.class);

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

    public final NumberPath<Long> id = createNumber("id", Long.class);

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

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QWorkup(String variable) {
        this(Workup.class, forVariable(variable), INITS);
    }

    public QWorkup(Path<? extends Workup> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWorkup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWorkup(PathMetadata metadata, PathInits inits) {
        this(Workup.class, metadata, inits);
    }

    public QWorkup(Class<? extends Workup> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.act = inits.isInitialized("act") ? new QAct(forProperty("act")) : null;
    }

}


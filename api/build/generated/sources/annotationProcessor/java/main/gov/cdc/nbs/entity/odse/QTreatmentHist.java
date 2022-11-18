package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTreatmentHist is a Querydsl query type for TreatmentHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTreatmentHist extends EntityPathBase<TreatmentHist> {

    private static final long serialVersionUID = 1339034275L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTreatmentHist treatmentHist = new QTreatmentHist("treatmentHist");

    public final StringPath activityDurationAmt = createString("activityDurationAmt");

    public final StringPath activityDurationUnitCd = createString("activityDurationUnitCd");

    public final DateTimePath<java.time.Instant> activityFromTime = createDateTime("activityFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> activityToTime = createDateTime("activityToTime", java.time.Instant.class);

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath cd = createString("cd");

    public final StringPath cdDescTxt = createString("cdDescTxt");

    public final StringPath cdSystemCd = createString("cdSystemCd");

    public final StringPath cdSystemDescTxt = createString("cdSystemDescTxt");

    public final StringPath cdVersion = createString("cdVersion");

    public final StringPath classCd = createString("classCd");

    public final QTreatmentHistId id;

    public final StringPath jurisdictionCd = createString("jurisdictionCd");

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final StringPath progAreaCd = createString("progAreaCd");

    public final NumberPath<Long> programJurisdictionOid = createNumber("programJurisdictionOid", Long.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final ComparablePath<Character> sharedInd = createComparable("sharedInd", Character.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final QTreatment treatmentUid;

    public final StringPath txt = createString("txt");

    public QTreatmentHist(String variable) {
        this(TreatmentHist.class, forVariable(variable), INITS);
    }

    public QTreatmentHist(Path<? extends TreatmentHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTreatmentHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTreatmentHist(PathMetadata metadata, PathInits inits) {
        this(TreatmentHist.class, metadata, inits);
    }

    public QTreatmentHist(Class<? extends TreatmentHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QTreatmentHistId(forProperty("id")) : null;
        this.treatmentUid = inits.isInitialized("treatmentUid") ? new QTreatment(forProperty("treatmentUid"), inits.get("treatmentUid")) : null;
    }

}


package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPatientEncounterHist is a Querydsl query type for PatientEncounterHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPatientEncounterHist extends EntityPathBase<PatientEncounterHist> {

    private static final long serialVersionUID = -928359193L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPatientEncounterHist patientEncounterHist = new QPatientEncounterHist("patientEncounterHist");

    public final StringPath activityDurationAmt = createString("activityDurationAmt");

    public final StringPath activityDurationUnitCd = createString("activityDurationUnitCd");

    public final DateTimePath<java.time.Instant> activityFromTime = createDateTime("activityFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> activityToTime = createDateTime("activityToTime", java.time.Instant.class);

    public final StringPath acuityLevelCd = createString("acuityLevelCd");

    public final StringPath acuityLevelDescTxt = createString("acuityLevelDescTxt");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath admissionSourceCd = createString("admissionSourceCd");

    public final StringPath admissionSourceDescTxt = createString("admissionSourceDescTxt");

    public final ComparablePath<Character> birthEncounterInd = createComparable("birthEncounterInd", Character.class);

    public final StringPath cd = createString("cd");

    public final StringPath cdDescTxt = createString("cdDescTxt");

    public final StringPath confidentialityCd = createString("confidentialityCd");

    public final StringPath confidentialityDescTxt = createString("confidentialityDescTxt");

    public final StringPath effectiveDurationAmt = createString("effectiveDurationAmt");

    public final StringPath effectiveDurationUnitCd = createString("effectiveDurationUnitCd");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final QPatientEncounterHistId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final QPatientEncounter patientEncounterUid;

    public final StringPath priorityCd = createString("priorityCd");

    public final StringPath priorityDescTxt = createString("priorityDescTxt");

    public final NumberPath<Long> programJurisdictionOid = createNumber("programJurisdictionOid", Long.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath referralSourceCd = createString("referralSourceCd");

    public final StringPath referralSourceDescTxt = createString("referralSourceDescTxt");

    public final NumberPath<Short> repeatNbr = createNumber("repeatNbr", Short.class);

    public final ComparablePath<Character> sharedInd = createComparable("sharedInd", Character.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath txt = createString("txt");

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public QPatientEncounterHist(String variable) {
        this(PatientEncounterHist.class, forVariable(variable), INITS);
    }

    public QPatientEncounterHist(Path<? extends PatientEncounterHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPatientEncounterHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPatientEncounterHist(PathMetadata metadata, PathInits inits) {
        this(PatientEncounterHist.class, metadata, inits);
    }

    public QPatientEncounterHist(Class<? extends PatientEncounterHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QPatientEncounterHistId(forProperty("id")) : null;
        this.patientEncounterUid = inits.isInitialized("patientEncounterUid") ? new QPatientEncounter(forProperty("patientEncounterUid"), inits.get("patientEncounterUid")) : null;
    }

}


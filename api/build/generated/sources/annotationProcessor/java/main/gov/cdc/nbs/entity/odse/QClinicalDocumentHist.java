package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClinicalDocumentHist is a Querydsl query type for ClinicalDocumentHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClinicalDocumentHist extends EntityPathBase<ClinicalDocumentHist> {

    private static final long serialVersionUID = 191255175L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClinicalDocumentHist clinicalDocumentHist = new QClinicalDocumentHist("clinicalDocumentHist");

    public final StringPath activityDurationAmt = createString("activityDurationAmt");

    public final StringPath activityDurationUnitCd = createString("activityDurationUnitCd");

    public final DateTimePath<java.time.Instant> activityFromTime = createDateTime("activityFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> activityToTime = createDateTime("activityToTime", java.time.Instant.class);

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath cd = createString("cd");

    public final StringPath cdDescTxt = createString("cdDescTxt");

    public final QClinicalDocument clinicalDocumentUid;

    public final StringPath confidentialityCd = createString("confidentialityCd");

    public final StringPath confidentialityDescTxt = createString("confidentialityDescTxt");

    public final DateTimePath<java.time.Instant> copyFromTime = createDateTime("copyFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> copyToTime = createDateTime("copyToTime", java.time.Instant.class);

    public final StringPath effectiveDurationAmt = createString("effectiveDurationAmt");

    public final StringPath effectiveDurationUnitCd = createString("effectiveDurationUnitCd");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final QClinicalDocumentHistId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final StringPath practiceSettingCd = createString("practiceSettingCd");

    public final StringPath practiceSettingDescTxt = createString("practiceSettingDescTxt");

    public final NumberPath<Long> programJurisdictionOid = createNumber("programJurisdictionOid", Long.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final ComparablePath<Character> sharedInd = createComparable("sharedInd", Character.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath txt = createString("txt");

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public final NumberPath<Short> versionNbr = createNumber("versionNbr", Short.class);

    public QClinicalDocumentHist(String variable) {
        this(ClinicalDocumentHist.class, forVariable(variable), INITS);
    }

    public QClinicalDocumentHist(Path<? extends ClinicalDocumentHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClinicalDocumentHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClinicalDocumentHist(PathMetadata metadata, PathInits inits) {
        this(ClinicalDocumentHist.class, metadata, inits);
    }

    public QClinicalDocumentHist(Class<? extends ClinicalDocumentHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.clinicalDocumentUid = inits.isInitialized("clinicalDocumentUid") ? new QClinicalDocument(forProperty("clinicalDocumentUid"), inits.get("clinicalDocumentUid")) : null;
        this.id = inits.isInitialized("id") ? new QClinicalDocumentHistId(forProperty("id")) : null;
    }

}


package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPublicHealthCase is a Querydsl query type for PublicHealthCase
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPublicHealthCase extends EntityPathBase<PublicHealthCase> {

    private static final long serialVersionUID = -926076052L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPublicHealthCase publicHealthCase = new QPublicHealthCase("publicHealthCase");

    public final QAct act;

    public final StringPath activityDurationAmt = createString("activityDurationAmt");

    public final StringPath activityDurationUnitCd = createString("activityDurationUnitCd");

    public final DateTimePath<java.time.Instant> activityFromTime = createDateTime("activityFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> activityToTime = createDateTime("activityToTime", java.time.Instant.class);

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath caseClassCd = createString("caseClassCd");

    public final ComparablePath<Character> caseTypeCd = createComparable("caseTypeCd", Character.class);

    public final StringPath cd = createString("cd");

    public final StringPath cdDescTxt = createString("cdDescTxt");

    public final StringPath cdSystemCd = createString("cdSystemCd");

    public final StringPath cdSystemDescTxt = createString("cdSystemDescTxt");

    public final StringPath coinfectionId = createString("coinfectionId");

    public final StringPath confidentialityCd = createString("confidentialityCd");

    public final StringPath confidentialityDescTxt = createString("confidentialityDescTxt");

    public final StringPath contactInvStatusCd = createString("contactInvStatusCd");

    public final StringPath contactInvTxt = createString("contactInvTxt");

    public final StringPath countIntervalCd = createString("countIntervalCd");

    public final StringPath currProcessStateCd = createString("currProcessStateCd");

    public final StringPath dayCareIndCd = createString("dayCareIndCd");

    public final DateTimePath<java.time.Instant> deceasedTime = createDateTime("deceasedTime", java.time.Instant.class);

    public final StringPath detectionMethodCd = createString("detectionMethodCd");

    public final StringPath detectionMethodDescTxt = createString("detectionMethodDescTxt");

    public final DateTimePath<java.time.Instant> diagnosisTime = createDateTime("diagnosisTime", java.time.Instant.class);

    public final StringPath diseaseImportedCd = createString("diseaseImportedCd");

    public final StringPath diseaseImportedDescTxt = createString("diseaseImportedDescTxt");

    public final StringPath effectiveDurationAmt = createString("effectiveDurationAmt");

    public final StringPath effectiveDurationUnitCd = createString("effectiveDurationUnitCd");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final StringPath foodHandlerIndCd = createString("foodHandlerIndCd");

    public final NumberPath<Short> groupCaseCnt = createNumber("groupCaseCnt", Short.class);

    public final DateTimePath<java.time.Instant> hospitalizedAdminTime = createDateTime("hospitalizedAdminTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> hospitalizedDischargeTime = createDateTime("hospitalizedDischargeTime", java.time.Instant.class);

    public final NumberPath<java.math.BigDecimal> hospitalizedDurationAmt = createNumber("hospitalizedDurationAmt", java.math.BigDecimal.class);

    public final StringPath hospitalizedIndCd = createString("hospitalizedIndCd");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath importedCityDescTxt = createString("importedCityDescTxt");

    public final StringPath importedCountryCd = createString("importedCountryCd");

    public final StringPath importedCountyCd = createString("importedCountyCd");

    public final StringPath importedStateCd = createString("importedStateCd");

    public final DateTimePath<java.time.Instant> infectiousFromDate = createDateTime("infectiousFromDate", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> infectiousToDate = createDateTime("infectiousToDate", java.time.Instant.class);

    public final StringPath investigationStatusCd = createString("investigationStatusCd");

    public final DateTimePath<java.time.Instant> investigatorAssignedTime = createDateTime("investigatorAssignedTime", java.time.Instant.class);

    public final StringPath invPriorityCd = createString("invPriorityCd");

    public final StringPath jurisdictionCd = createString("jurisdictionCd");

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final StringPath mmwrWeek = createString("mmwrWeek");

    public final StringPath mmwrYear = createString("mmwrYear");

    public final DateTimePath<java.time.Instant> outbreakFromTime = createDateTime("outbreakFromTime", java.time.Instant.class);

    public final StringPath outbreakInd = createString("outbreakInd");

    public final StringPath outbreakName = createString("outbreakName");

    public final DateTimePath<java.time.Instant> outbreakToTime = createDateTime("outbreakToTime", java.time.Instant.class);

    public final StringPath outcomeCd = createString("outcomeCd");

    public final StringPath patAgeAtOnset = createString("patAgeAtOnset");

    public final StringPath patAgeAtOnsetUnitCd = createString("patAgeAtOnsetUnitCd");

    public final NumberPath<Long> patientGroupId = createNumber("patientGroupId", Long.class);

    public final StringPath pregnantIndCd = createString("pregnantIndCd");

    public final StringPath priorityCd = createString("priorityCd");

    public final StringPath progAreaCd = createString("progAreaCd");

    public final NumberPath<Long> programJurisdictionOid = createNumber("programJurisdictionOid", Long.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath referralBasisCd = createString("referralBasisCd");

    public final NumberPath<Short> repeatNbr = createNumber("repeatNbr", Short.class);

    public final StringPath rptCntyCd = createString("rptCntyCd");

    public final DateTimePath<java.time.Instant> rptFormCmpltTime = createDateTime("rptFormCmpltTime", java.time.Instant.class);

    public final StringPath rptSourceCd = createString("rptSourceCd");

    public final StringPath rptSourceCdDescTxt = createString("rptSourceCdDescTxt");

    public final DateTimePath<java.time.Instant> rptToCountyTime = createDateTime("rptToCountyTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> rptToStateTime = createDateTime("rptToStateTime", java.time.Instant.class);

    public final ComparablePath<Character> sharedInd = createComparable("sharedInd", Character.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath transmissionModeCd = createString("transmissionModeCd");

    public final StringPath transmissionModeDescTxt = createString("transmissionModeDescTxt");

    public final StringPath txt = createString("txt");

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QPublicHealthCase(String variable) {
        this(PublicHealthCase.class, forVariable(variable), INITS);
    }

    public QPublicHealthCase(Path<? extends PublicHealthCase> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPublicHealthCase(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPublicHealthCase(PathMetadata metadata, PathInits inits) {
        this(PublicHealthCase.class, metadata, inits);
    }

    public QPublicHealthCase(Class<? extends PublicHealthCase> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.act = inits.isInitialized("act") ? new QAct(forProperty("act")) : null;
    }

}


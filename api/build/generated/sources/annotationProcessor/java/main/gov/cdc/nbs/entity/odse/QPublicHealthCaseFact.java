package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPublicHealthCaseFact is a Querydsl query type for PublicHealthCaseFact
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPublicHealthCaseFact extends EntityPathBase<PublicHealthCaseFact> {

    private static final long serialVersionUID = 1863247864L;

    public static final QPublicHealthCaseFact publicHealthCaseFact = new QPublicHealthCaseFact("publicHealthCaseFact");

    public final NumberPath<Short> adultsInHouseNbr = createNumber("adultsInHouseNbr", Short.class);

    public final StringPath ageCategoryCd = createString("ageCategoryCd");

    public final NumberPath<Short> ageInMonths = createNumber("ageInMonths", Short.class);

    public final NumberPath<Short> ageInYears = createNumber("ageInYears", Short.class);

    public final NumberPath<java.math.BigDecimal> ageReported = createNumber("ageReported", java.math.BigDecimal.class);

    public final DateTimePath<java.time.Instant> ageReportedTime = createDateTime("ageReportedTime", java.time.Instant.class);

    public final StringPath ageReportedUnitCd = createString("ageReportedUnitCd");

    public final StringPath ageReportedUnitDescTxt = createString("ageReportedUnitDescTxt");

    public final StringPath awarenessCd = createString("awarenessCd");

    public final StringPath awarenessDescTxt = createString("awarenessDescTxt");

    public final ComparablePath<Character> birthGenderCd = createComparable("birthGenderCd", Character.class);

    public final StringPath birthGenderDescTxt = createString("birthGenderDescTxt");

    public final NumberPath<Short> birthOrderNbr = createNumber("birthOrderNbr", Short.class);

    public final DateTimePath<java.time.Instant> birthTime = createDateTime("birthTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> birthTimeCalc = createDateTime("birthTimeCalc", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> birthTimeStd = createDateTime("birthTimeStd", java.time.Instant.class);

    public final StringPath caseClassCd = createString("caseClassCd");

    public final StringPath caseClassDescTxt = createString("caseClassDescTxt");

    public final ComparablePath<Character> caseTypeCd = createComparable("caseTypeCd", Character.class);

    public final StringPath cdSystemCd = createString("cdSystemCd");

    public final StringPath cdSystemDescTxt = createString("cdSystemDescTxt");

    public final StringPath censusBlockCd = createString("censusBlockCd");

    public final StringPath censusMinorCivilDivisionCd = createString("censusMinorCivilDivisionCd");

    public final StringPath censusTrackCd = createString("censusTrackCd");

    public final NumberPath<Short> childrenInHouseNbr = createNumber("childrenInHouseNbr", Short.class);

    public final StringPath cityCd = createString("cityCd");

    public final StringPath cityDescTxt = createString("cityDescTxt");

    public final StringPath cntryCd = createString("cntryCd");

    public final StringPath cntryDescTxt = createString("cntryDescTxt");

    public final StringPath cntyCd = createString("cntyCd");

    public final StringPath cntyCodeDescTxt = createString("cntyCodeDescTxt");

    public final StringPath confidentialityCd = createString("confidentialityCd");

    public final StringPath confidentialityDescTxt = createString("confidentialityDescTxt");

    public final StringPath confirmationMethodCd = createString("confirmationMethodCd");

    public final StringPath confirmationMethodDescTxt = createString("confirmationMethodDescTxt");

    public final DateTimePath<java.time.Instant> confirmationMethodTime = createDateTime("confirmationMethodTime", java.time.Instant.class);

    public final StringPath county = createString("county");

    public final ComparablePath<Character> currSexCd = createComparable("currSexCd", Character.class);

    public final StringPath currSexDescTxt = createString("currSexDescTxt");

    public final StringPath deceasedIndCd = createString("deceasedIndCd");

    public final DateTimePath<java.time.Instant> deceasedTime = createDateTime("deceasedTime", java.time.Instant.class);

    public final StringPath detectionMethodCd = createString("detectionMethodCd");

    public final StringPath detectionMethodDescTxt = createString("detectionMethodDescTxt");

    public final DateTimePath<java.time.Instant> diagnosisDate = createDateTime("diagnosisDate", java.time.Instant.class);

    public final StringPath diseaseImportedCd = createString("diseaseImportedCd");

    public final StringPath diseaseImportedDescTxt = createString("diseaseImportedDescTxt");

    public final StringPath educationLevelCd = createString("educationLevelCd");

    public final StringPath educationLevelDescTxt = createString("educationLevelDescTxt");

    public final StringPath elpClassCd = createString("elpClassCd");

    public final DateTimePath<java.time.Instant> elpFromTime = createDateTime("elpFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> elpToTime = createDateTime("elpToTime", java.time.Instant.class);

    public final StringPath elpUseCd = createString("elpUseCd");

    public final StringPath ethnicGroupInd = createString("ethnicGroupInd");

    public final StringPath ethnicGroupIndDesc = createString("ethnicGroupIndDesc");

    public final DateTimePath<java.time.Instant> eventDate = createDateTime("eventDate", java.time.Instant.class);

    public final StringPath eventType = createString("eventType");

    public final DateTimePath<java.time.Instant> firstNotificationdate = createDateTime("firstNotificationdate", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> firstNotificationSenddate = createDateTime("firstNotificationSenddate", java.time.Instant.class);

    public final StringPath firstNotificationStatus = createString("firstNotificationStatus");

    public final NumberPath<Long> firstNotificationSubmittedBy = createNumber("firstNotificationSubmittedBy", Long.class);

    public final NumberPath<Float> geoLatitude = createNumber("geoLatitude", Float.class);

    public final NumberPath<Float> geoLongitude = createNumber("geoLongitude", Float.class);

    public final NumberPath<java.math.BigDecimal> groupCaseCnt = createNumber("groupCaseCnt", java.math.BigDecimal.class);

    public final StringPath hospitalizedInd = createString("hospitalizedInd");

    public final DateTimePath<java.time.Instant> hsptlAdmissionDt = createDateTime("hsptlAdmissionDt", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> hsptlDischargeDt = createDateTime("hsptlDischargeDt", java.time.Instant.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> investigationstartdate = createDateTime("investigationstartdate", java.time.Instant.class);

    public final StringPath investigationStatusCd = createString("investigationStatusCd");

    public final StringPath investigationStatusDescTxt = createString("investigationStatusDescTxt");

    public final DateTimePath<java.time.Instant> investigatorAssigneddate = createDateTime("investigatorAssigneddate", java.time.Instant.class);

    public final StringPath investigatorName = createString("investigatorName");

    public final StringPath investigatorPhone = createString("investigatorPhone");

    public final StringPath jurisdiction = createString("jurisdiction");

    public final StringPath jurisdictionCd = createString("jurisdictionCd");

    public final DateTimePath<java.time.Instant> lastNotificationdate = createDateTime("lastNotificationdate", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> lastNotificationSenddate = createDateTime("lastNotificationSenddate", java.time.Instant.class);

    public final NumberPath<Long> lastNotificationSubmittedBy = createNumber("lastNotificationSubmittedBy", Long.class);

    public final DateTimePath<java.time.Instant> lastupdate = createDateTime("lastupdate", java.time.Instant.class);

    public final StringPath localId = createString("localId");

    public final StringPath maritalStatusCd = createString("maritalStatusCd");

    public final StringPath maritalStatusDescTxt = createString("maritalStatusDescTxt");

    public final DateTimePath<java.time.Instant> martRecordCreationDate = createDateTime("martRecordCreationDate", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> martRecordCreationTime = createDateTime("martRecordCreationTime", java.time.Instant.class);

    public final NumberPath<java.math.BigDecimal> mmwrWeek = createNumber("mmwrWeek", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> mmwrYear = createNumber("mmwrYear", java.math.BigDecimal.class);

    public final StringPath msaCongressDistrictCd = createString("msaCongressDistrictCd");

    public final StringPath multipleBirthInd = createString("multipleBirthInd");

    public final NumberPath<Integer> notifCreatedCount = createNumber("notifCreatedCount", Integer.class);

    public final StringPath notifcurrentstate = createString("notifcurrentstate");

    public final DateTimePath<java.time.Instant> notificationdate = createDateTime("notificationdate", java.time.Instant.class);

    public final StringPath notificationLocalId = createString("notificationLocalId");

    public final NumberPath<Integer> notifSentCount = createNumber("notifSentCount", Integer.class);

    public final StringPath notitxt = createString("notitxt");

    public final StringPath occupationCd = createString("occupationCd");

    public final StringPath occupationDescTxt = createString("occupationDescTxt");

    public final DateTimePath<java.time.Instant> onSetDate = createDateTime("onSetDate", java.time.Instant.class);

    public final StringPath organizationName = createString("organizationName");

    public final DateTimePath<java.time.Instant> outbreakFromTime = createDateTime("outbreakFromTime", java.time.Instant.class);

    public final StringPath outbreakInd = createString("outbreakInd");

    public final StringPath outbreakName = createString("outbreakName");

    public final StringPath outbreakNameDesc = createString("outbreakNameDesc");

    public final DateTimePath<java.time.Instant> outbreakToTime = createDateTime("outbreakToTime", java.time.Instant.class);

    public final StringPath outcomeCd = createString("outcomeCd");

    public final StringPath outcomeDescTxt = createString("outcomeDescTxt");

    public final StringPath parTypeCd = createString("parTypeCd");

    public final NumberPath<java.math.BigDecimal> patAgeAtOnset = createNumber("patAgeAtOnset", java.math.BigDecimal.class);

    public final StringPath patAgeAtOnsetUnitCd = createString("patAgeAtOnsetUnitCd");

    public final StringPath patAgeAtOnsetUnitDescTxt = createString("patAgeAtOnsetUnitDescTxt");

    public final StringPath patientName = createString("patientName");

    public final StringPath personCd = createString("personCd");

    public final StringPath personCodeDesc = createString("personCodeDesc");

    public final StringPath personLocalId = createString("personLocalId");

    public final NumberPath<Long> personParentUid = createNumber("personParentUid", Long.class);

    public final NumberPath<Long> personUid = createNumber("personUid", Long.class);

    public final DateTimePath<java.time.Instant> phcAddTime = createDateTime("phcAddTime", java.time.Instant.class);

    public final StringPath phcCode = createString("phcCode");

    public final StringPath phcCodeDesc = createString("phcCodeDesc");

    public final StringPath phcCodeShortDesc = createString("phcCodeShortDesc");

    public final StringPath phctxt = createString("phctxt");

    public final NumberPath<Long> postalLocatorUid = createNumber("postalLocatorUid", Long.class);

    public final StringPath primLangCd = createString("primLangCd");

    public final StringPath primLangDescTxt = createString("primLangDescTxt");

    public final StringPath progAreaCd = createString("progAreaCd");

    public final StringPath progAreaDescTxt = createString("progAreaDescTxt");

    public final NumberPath<Long> programJurisdictionOid = createNumber("programJurisdictionOid", Long.class);

    public final StringPath providerName = createString("providerName");

    public final StringPath providerPhone = createString("providerPhone");

    public final StringPath pstRecordStatusCd = createString("pstRecordStatusCd");

    public final DateTimePath<java.time.Instant> pstRecordStatusTime = createDateTime("pstRecordStatusTime", java.time.Instant.class);

    public final StringPath raceConcatenatedDescTxt = createString("raceConcatenatedDescTxt");

    public final StringPath raceConcatenatedTxt = createString("raceConcatenatedTxt");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final StringPath regionDistrictCd = createString("regionDistrictCd");

    public final DateTimePath<java.time.Instant> reportDate = createDateTime("reportDate", java.time.Instant.class);

    public final StringPath reporterName = createString("reporterName");

    public final StringPath reporterPhone = createString("reporterPhone");

    public final StringPath rptCntyCd = createString("rptCntyCd");

    public final StringPath rptCntyDescTxt = createString("rptCntyDescTxt");

    public final DateTimePath<java.time.Instant> rptFormCmpltTime = createDateTime("rptFormCmpltTime", java.time.Instant.class);

    public final StringPath rptSourceCd = createString("rptSourceCd");

    public final StringPath rptSourceDescTxt = createString("rptSourceDescTxt");

    public final DateTimePath<java.time.Instant> rptToCountyTime = createDateTime("rptToCountyTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> rptToStateTime = createDateTime("rptToStateTime", java.time.Instant.class);

    public final StringPath sharedInd = createString("sharedInd");

    public final StringPath state = createString("state");

    public final StringPath stateCaseId = createString("stateCaseId");

    public final StringPath stateCd = createString("stateCd");

    public final StringPath stateCodeShortDescTxt = createString("stateCodeShortDescTxt");

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final StringPath streetAddr1 = createString("streetAddr1");

    public final StringPath streetAddr2 = createString("streetAddr2");

    public final DateTimePath<java.time.Instant> subAddrAsOfDate = createDateTime("subAddrAsOfDate", java.time.Instant.class);

    public final StringPath zipCd = createString("zipCd");

    public QPublicHealthCaseFact(String variable) {
        super(PublicHealthCaseFact.class, forVariable(variable));
    }

    public QPublicHealthCaseFact(Path<? extends PublicHealthCaseFact> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPublicHealthCaseFact(PathMetadata metadata) {
        super(PublicHealthCaseFact.class, metadata);
    }

}


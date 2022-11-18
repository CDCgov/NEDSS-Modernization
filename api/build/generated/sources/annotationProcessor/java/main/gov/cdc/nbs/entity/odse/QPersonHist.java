package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPersonHist is a Querydsl query type for PersonHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPersonHist extends EntityPathBase<PersonHist> {

    private static final long serialVersionUID = -1180692018L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPersonHist personHist = new QPersonHist("personHist");

    public final StringPath additionalGenderCd = createString("additionalGenderCd");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath administrativeGenderCd = createString("administrativeGenderCd");

    public final NumberPath<Short> adultsInHouseNbr = createNumber("adultsInHouseNbr", Short.class);

    public final NumberPath<Short> ageCalc = createNumber("ageCalc", Short.class);

    public final DateTimePath<java.time.Instant> ageCalcTime = createDateTime("ageCalcTime", java.time.Instant.class);

    public final ComparablePath<Character> ageCalcUnitCd = createComparable("ageCalcUnitCd", Character.class);

    public final StringPath ageCategoryCd = createString("ageCategoryCd");

    public final StringPath ageReported = createString("ageReported");

    public final DateTimePath<java.time.Instant> ageReportedTime = createDateTime("ageReportedTime", java.time.Instant.class);

    public final StringPath ageReportedUnitCd = createString("ageReportedUnitCd");

    public final DateTimePath<java.time.Instant> asOfDateAdmin = createDateTime("asOfDateAdmin", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> asOfDateEthnicity = createDateTime("asOfDateEthnicity", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> asOfDateGeneral = createDateTime("asOfDateGeneral", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> asOfDateMorbidity = createDateTime("asOfDateMorbidity", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> asOfDateSex = createDateTime("asOfDateSex", java.time.Instant.class);

    public final StringPath birthCityCd = createString("birthCityCd");

    public final StringPath birthCityDescTxt = createString("birthCityDescTxt");

    public final StringPath birthCntryCd = createString("birthCntryCd");

    public final ComparablePath<Character> birthGenderCd = createComparable("birthGenderCd", Character.class);

    public final NumberPath<Short> birthOrderNbr = createNumber("birthOrderNbr", Short.class);

    public final StringPath birthStateCd = createString("birthStateCd");

    public final DateTimePath<java.time.Instant> birthTime = createDateTime("birthTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> birthTimeCalc = createDateTime("birthTimeCalc", java.time.Instant.class);

    public final StringPath cd = createString("cd");

    public final StringPath cdDescTxt = createString("cdDescTxt");

    public final StringPath cellPhoneNbr = createString("cellPhoneNbr");

    public final NumberPath<Short> childrenInHouseNbr = createNumber("childrenInHouseNbr", Short.class);

    public final ComparablePath<Character> currSexCd = createComparable("currSexCd", Character.class);

    public final StringPath deceasedIndCd = createString("deceasedIndCd");

    public final DateTimePath<java.time.Instant> deceasedTime = createDateTime("deceasedTime", java.time.Instant.class);

    public final ComparablePath<Character> dedupMatchInd = createComparable("dedupMatchInd", Character.class);

    public final StringPath description = createString("description");

    public final StringPath dlNum = createString("dlNum");

    public final StringPath dlStateCd = createString("dlStateCd");

    public final StringPath educationLevelCd = createString("educationLevelCd");

    public final StringPath educationLevelDescTxt = createString("educationLevelDescTxt");

    public final StringPath edxInd = createString("edxInd");

    public final StringPath eharsId = createString("eharsId");

    public final ComparablePath<Character> electronicInd = createComparable("electronicInd", Character.class);

    public final StringPath ethnicGroupDescTxt = createString("ethnicGroupDescTxt");

    public final StringPath ethnicGroupInd = createString("ethnicGroupInd");

    public final StringPath ethnicityGroupCd = createString("ethnicityGroupCd");

    public final NumberPath<Short> ethnicityGroupSeqNbr = createNumber("ethnicityGroupSeqNbr", Short.class);

    public final StringPath ethnicUnkReasonCd = createString("ethnicUnkReasonCd");

    public final StringPath firstNm = createString("firstNm");

    public final NumberPath<Integer> groupNbr = createNumber("groupNbr", Integer.class);

    public final DateTimePath<java.time.Instant> groupTime = createDateTime("groupTime", java.time.Instant.class);

    public final StringPath hmCityCd = createString("hmCityCd");

    public final StringPath hmCityDescTxt = createString("hmCityDescTxt");

    public final StringPath hmCntryCd = createString("hmCntryCd");

    public final StringPath hmCntyCd = createString("hmCntyCd");

    public final StringPath hmEmailAddr = createString("hmEmailAddr");

    public final StringPath hmPhoneCntryCd = createString("hmPhoneCntryCd");

    public final StringPath hmPhoneNbr = createString("hmPhoneNbr");

    public final StringPath hmStateCd = createString("hmStateCd");

    public final StringPath hmStreetAddr1 = createString("hmStreetAddr1");

    public final StringPath hmStreetAddr2 = createString("hmStreetAddr2");

    public final StringPath hmZipCd = createString("hmZipCd");

    public final QPersonHistId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath lastNm = createString("lastNm");

    public final StringPath localId = createString("localId");

    public final StringPath maritalStatusCd = createString("maritalStatusCd");

    public final StringPath maritalStatusDescTxt = createString("maritalStatusDescTxt");

    public final StringPath medicaidNum = createString("medicaidNum");

    public final StringPath middleNm = createString("middleNm");

    public final StringPath mothersMaidenNm = createString("mothersMaidenNm");

    public final StringPath multipleBirthInd = createString("multipleBirthInd");

    public final StringPath nmPrefix = createString("nmPrefix");

    public final StringPath nmSuffix = createString("nmSuffix");

    public final StringPath occupationCd = createString("occupationCd");

    public final NumberPath<Long> personParentUid = createNumber("personParentUid", Long.class);

    public final QPerson personUid;

    public final StringPath preferredGenderCd = createString("preferredGenderCd");

    public final StringPath preferredNm = createString("preferredNm");

    public final StringPath primLangCd = createString("primLangCd");

    public final StringPath primLangDescTxt = createString("primLangDescTxt");

    public final StringPath raceCategoryCd = createString("raceCategoryCd");

    public final StringPath raceCd = createString("raceCd");

    public final StringPath raceDescTxt = createString("raceDescTxt");

    public final NumberPath<Short> raceSeqNbr = createNumber("raceSeqNbr", Short.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath sexUnkReasonCd = createString("sexUnkReasonCd");

    public final StringPath speaksEnglishCd = createString("speaksEnglishCd");

    public final StringPath ssn = createString("ssn");

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final ComparablePath<Character> survivedIndCd = createComparable("survivedIndCd", Character.class);

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public final StringPath wkCityCd = createString("wkCityCd");

    public final StringPath wkCityDescTxt = createString("wkCityDescTxt");

    public final StringPath wkCntryCd = createString("wkCntryCd");

    public final StringPath wkCntyCd = createString("wkCntyCd");

    public final StringPath wkEmailAddr = createString("wkEmailAddr");

    public final StringPath wkPhoneCntryCd = createString("wkPhoneCntryCd");

    public final StringPath wkPhoneNbr = createString("wkPhoneNbr");

    public final StringPath wkStateCd = createString("wkStateCd");

    public final StringPath wkStreetAddr1 = createString("wkStreetAddr1");

    public final StringPath wkStreetAddr2 = createString("wkStreetAddr2");

    public final StringPath wkZipCd = createString("wkZipCd");

    public QPersonHist(String variable) {
        this(PersonHist.class, forVariable(variable), INITS);
    }

    public QPersonHist(Path<? extends PersonHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPersonHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPersonHist(PathMetadata metadata, PathInits inits) {
        this(PersonHist.class, metadata, inits);
    }

    public QPersonHist(Class<? extends PersonHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QPersonHistId(forProperty("id")) : null;
        this.personUid = inits.isInitialized("personUid") ? new QPerson(forProperty("personUid"), inits.get("personUid")) : null;
    }

}


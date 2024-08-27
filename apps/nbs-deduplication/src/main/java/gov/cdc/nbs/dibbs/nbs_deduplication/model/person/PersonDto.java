package gov.cdc.nbs.dibbs.nbs_deduplication.model.person;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gov.cdc.nbs.dibbs.nbs_deduplication.model.BaseContainer;
import gov.cdc.nbs.dibbs.nbs_deduplication.model.RootDtoInterface;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@JsonIgnoreProperties({"dedupMatchInd", "groupNbr", "groupTime", "personUid", "personParentUid", "addReasonCd", "addTime", "addUserId", "administrativeGenderCd", "ageCalc",
    "ageCalcTime", "ageCalcUnitCd", "ageCategoryCd", "ageReported", "ageReportedTime", "ageReportedUnitCd", "asOfDateAdmin", "asOfDateEthnicity", "asOfDateGeneral",
    "asOfDateMorbidity", "asOfDateSex", "birthGenderCd", "birthOrderNbr", "birthOrderNbrStr", "cd", "cdDescTxt", "currSexCd", "deceasedIndCd", "deceasedTime",
    "description", "educationLevelCd", "educationLevelDescTxt", "electronicInd", "ethnicGroupInd", "lastChgReasonCd", "lastChgTime", "lastChgUserId", "localId",
    "maritalStatusCd", "maritalStatusDescTxt", "mothersMaidenNm", "multipleBirthInd", "occupationCd", "preferredGenderCd", "primLangCd", "primLangDescTxt",
    "recordStatusCd", "recordStatusTime", "statusCd", "statusTime", "survivedIndCd", "userAffiliationTxt", "firstNm", "lastNm", "middleNm", "nmPrefix", "nmSuffix",
    "preferredNm", "hmStreetAddr1", "hmStreetAddr2", "hmCityCd", "hmCityDescTxt", "hmStateCd", "hmZipCd", "hmCntyCd", "hmCntryCd", "hmPhoneNbr", "hmPhoneCntryCd",
    "hmEmailAddr", "cellPhoneNbr", "wkStreetAddr1", "wkStreetAddr2", "wkCityCd", "wkCityDescTxt", "wkStateCd", "wkZipCd", "wkCntyCd", "wkCntryCd", "wkPhoneNbr",
    "wkPhoneCntryCd", "wkEmailAddr", "SSN", "medicaidNum", "dlNum", "dlStateCd", "raceCd", "raceSeqNbr", "raceCategoryCd", "ethnicityGroupCd", "ethnicGroupSeqNbr",
    "adultsInHouseNbr", "adultsInHouseNbrStr", "childrenInHouseNbr", "childrenInHouseNbrStr", "birthCityCd", "birthCityDescTxt", "birthCntryCd", "birthStateCd",
    "raceDescTxt", "ethnicGroupDescTxt", "versionCtrlNbr", "progAreaCd", "jurisdictionCd", "programJurisdictionOid", "sharedInd", "edxInd", "isCaseInd",
    "speaksEnglishCd", "additionalGenderCd", "eharsId", "ethnicUnkReasonCd", "sexUnkReasonCd", "isReentrant"})
public class PersonDto extends BaseContainer implements RootDtoInterface {
    private static final long serialVersionUID = 1L;




    private String dedupMatchInd;
    private Integer groupNbr;
    private Timestamp groupTime;
    private Long personUid;
    private Long personParentUid;
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private String administrativeGenderCd;
    private Integer ageCalc;
    private Timestamp ageCalcTime;
    private String ageCalcUnitCd;
    private String ageCategoryCd;
    private String ageReported;
    private Timestamp ageReportedTime;
    private String ageReportedUnitCd;
    private Timestamp asOfDateAdmin;
    private Timestamp asOfDateEthnicity;
    private Timestamp asOfDateGeneral;
    private Timestamp asOfDateMorbidity;
    private Timestamp asOfDateSex;
    private String birthGenderCd;
    private Integer birthOrderNbr;
    private String birthOrderNbrStr;
    private Timestamp birthTime;
    private Timestamp birthTimeCalc;
    private String cd;
    private String cdDescTxt;
    private String currSexCd;
    private String deceasedIndCd;
    private Timestamp deceasedTime;
    private String description;
    private String educationLevelCd;
    private String educationLevelDescTxt;
    private String electronicInd;
    private String ethnicGroupInd;
    private String lastChgReasonCd;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String localId;
    private String maritalStatusCd;
    private String maritalStatusDescTxt;
    private String mothersMaidenNm;
    private String multipleBirthInd;
    private String occupationCd;

    private String preferredGenderCd;
    private String primLangCd;
    private String primLangDescTxt;
    private String recordStatusCd;
    private Timestamp recordStatusTime;
    private String statusCd;
    private Timestamp statusTime;
    private String survivedIndCd;
    private String userAffiliationTxt;
    private String firstNm;
    private String lastNm;
    private String middleNm;
    private String nmPrefix;
    private String nmSuffix;
    private String preferredNm;
    private String hmStreetAddr1;
    private String hmStreetAddr2;
    private String hmCityCd;
    private String hmCityDescTxt;
    private String hmStateCd;
    private String hmZipCd;
    private String hmCntyCd;
    private String hmCntryCd;
    private String hmPhoneNbr;
    private String hmPhoneCntryCd;
    private String hmEmailAddr;
    private String cellPhoneNbr;
    private String wkStreetAddr1;
    private String wkStreetAddr2;
    private String wkCityCd;
    private String wkCityDescTxt;
    private String wkStateCd;
    private String wkZipCd;
    private String wkCntyCd;
    private String wkCntryCd;
    private String wkPhoneNbr;
    private String wkPhoneCntryCd;
    private String wkEmailAddr;
    private String SSN;
    private String medicaidNum;
    private String dlNum;
    private String dlStateCd;
    private String raceCd;
    private Integer raceSeqNbr;
    private String raceCategoryCd;
    private String ethnicityGroupCd;
    private Integer ethnicGroupSeqNbr;
    private Integer adultsInHouseNbr;
    private String adultsInHouseNbrStr;
    private Integer childrenInHouseNbr;
    private String childrenInHouseNbrStr;
    private String birthCityCd;
    private String birthCityDescTxt;
    private String birthCntryCd;
    private String birthStateCd;
    private String raceDescTxt;
    private String ethnicGroupDescTxt;
    private Integer versionCtrlNbr;
    private String progAreaCd = null;
    private String jurisdictionCd = null;
    private Long programJurisdictionOid = null;
    private String sharedInd = null;
    private String edxInd =null;
    private boolean isCaseInd= false;
    private String speaksEnglishCd;
    private String additionalGenderCd;
    private String eharsId;
    private String ethnicUnkReasonCd;
    private String sexUnkReasonCd;
    private boolean isReentrant= false;

    // Same on PersonHIST
    public String getSuperclass() {
        return superClassType;
    }

    @Override
    public Long getUid() {
        return personUid;
    }

    public PersonDto() {
        itDirty = false;
        itNew = true;
        itDelete = false;
    }



}

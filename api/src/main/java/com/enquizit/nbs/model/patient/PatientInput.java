package com.enquizit.nbs.model.patient;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientInput {
    private String addReasonCd;
    private LocalDateTime addTime;
    private Long addUserId;
    private String administrativeGenderCd;
    private Short ageCalc;
    private LocalDateTime ageCalcTime;
    private String ageCalcUnitCd;
    private String ageCategoryCd;
    private String ageReported;
    private LocalDateTime ageReportedTime;
    private String ageReportedUnitCd;
    private String birthGenderCd;
    private Short birthOrderNbr;
    private LocalDateTime birthTime;
    private LocalDateTime birthTimeCalc;
    private String cd;
    private String cdDescTxt;
    private String currSexCd;
    private String deceasedIndCd;
    private LocalDateTime deceasedTime;
    private String description;
    private String educationLevelCd;
    private String educationLevelDescTxt;
    private String ethnicGroupInd;
    private String lastChgReasonCd;
    private LocalDateTime lastChgTime;
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
    private LocalDateTime recordStatusTime;
    private String statusCd;
    private LocalDateTime statusTime;
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
    private String ssn;
    private String medicaidNum;
    private String dlNum;
    private String dlStateCd;
    private String raceCd;
    private Short raceSeqNbr;
    private String raceCategoryCd;
    private String ethnicityGroupCd;
    private Short ethnicGroupSeqNbr;
    private Short adultsInHouseNbr;
    private Short childrenInHouseNbr;
    private String birthCityCd;
    private String birthCityDescTxt;
    private String birthCntryCd;
    private String birthStateCd;
    private String raceDescTxt;
    private String ethnicGroupDescTxt;
    private short versionCtrlNbr;
    private LocalDateTime asOfLocalDateTimeAdmin;
    private LocalDateTime asOfLocalDateTimeEthnicity;
    private LocalDateTime asOfLocalDateTimeGeneral;
    private LocalDateTime asOfLocalDateTimeMorbidity;
    private LocalDateTime asOfLocalDateTimeSex;
    private String electronicInd;
    private Long personParentUid;
    private String dedupMatchInd;
    private Integer groupNbr;
    private LocalDateTime groupTime;
    private String edxInd;
    private String speaksEnglishCd;
    private String additionalGenderCd;
    private String eharsId;
    private String ethnicUnkReasonCd;
    private String sexUnkReasonCd;

    public Patient toPatient(long Id) {
        return new Patient(Id, addReasonCd, addTime, addUserId, administrativeGenderCd, ageCalc, ageCalcTime,
                ageCalcUnitCd, ageCategoryCd, ageReported, ageReportedTime, ageReportedUnitCd, birthGenderCd,
                birthOrderNbr, birthTime, birthTimeCalc, cd, cdDescTxt, currSexCd, deceasedIndCd, deceasedTime,
                description, educationLevelCd, educationLevelDescTxt, ethnicGroupInd, lastChgReasonCd, lastChgTime,
                lastChgUserId, localId, maritalStatusCd, maritalStatusDescTxt, mothersMaidenNm, multipleBirthInd,
                occupationCd, preferredGenderCd, primLangCd, primLangDescTxt, recordStatusCd, recordStatusTime,
                statusCd, statusTime, survivedIndCd, userAffiliationTxt, firstNm, lastNm, middleNm, nmPrefix, nmSuffix,
                preferredNm, hmStreetAddr1, hmStreetAddr2, hmCityCd, hmCityDescTxt, hmStateCd, hmZipCd, hmCntyCd,
                hmCntryCd, hmPhoneNbr, hmPhoneCntryCd, hmEmailAddr, cellPhoneNbr, wkStreetAddr1, wkStreetAddr2,
                wkCityCd, wkCityDescTxt, wkStateCd, wkZipCd, wkCntyCd, wkCntryCd, wkPhoneNbr, wkPhoneCntryCd,
                wkEmailAddr, ssn, medicaidNum, dlNum, dlStateCd, raceCd, raceSeqNbr, raceCategoryCd, ethnicityGroupCd,
                ethnicGroupSeqNbr, adultsInHouseNbr, childrenInHouseNbr, birthCityCd, birthCityDescTxt, birthCntryCd,
                birthStateCd, raceDescTxt, ethnicGroupDescTxt, versionCtrlNbr, asOfLocalDateTimeAdmin,
                asOfLocalDateTimeEthnicity, asOfLocalDateTimeGeneral, asOfLocalDateTimeMorbidity, asOfLocalDateTimeSex,
                electronicInd, personParentUid, dedupMatchInd, groupNbr, groupTime, edxInd, speaksEnglishCd,
                additionalGenderCd, eharsId, ethnicUnkReasonCd, sexUnkReasonCd);
    }
}

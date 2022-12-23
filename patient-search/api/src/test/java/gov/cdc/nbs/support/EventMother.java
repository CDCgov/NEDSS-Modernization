package gov.cdc.nbs.support;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import gov.cdc.nbs.entity.elasticsearch.Investigation;
import gov.cdc.nbs.entity.elasticsearch.LabReport;
import gov.cdc.nbs.entity.srte.JurisdictionCode;

public class EventMother {

        public static Long CREATED_BY = 999999L;
        public static Long UPDATED_BY = 999998L;

        // jurisdiction codes from NBS_SRTE.Jurisdiction_code
        public static Long DEKALB_CODE = 930005L;
        public static Long CLAYTON_CODE = 930006L;

        // from NBS_SRTE.Program_area_code
        public static Integer STD_ID = 15;
        public static Integer ARBO_ID = 13;

        public static Long DEKALB_ARBO_OID = (DEKALB_CODE * 100000L) + ARBO_ID;
        public static Long CLAYTON_STD_OID = (CLAYTON_CODE * 100000L) + STD_ID;

        public static Investigation investigation_bacterialVaginosis(Long personId) {
                return Investigation.builder()
                                // TODO .id("Test_bacterial_vaginosis")
                                // .subjectEntityUid(personId)
                                // .classCd("PSN")
                                // .personCd("PAT")
                                // .caseTypeCd("I")
                                // .moodCd("EVN")
                                // .cdDescTxt("Bacterial Vaginosis")
                                // .prog_area_cd("STD")
                                // .jurisdictionCd(CLAYTON_CODE)
                                // .pregnantIndCd("Y")
                                // .actIdSeq(2)
                                // .actIdTypeCd("CITY")
                                // .rootExtensionTxt("CityTypeRootExtensionText")
                                // .publicHealthCaseLocalId("CAS10001000GA01")
                                // .addUserId(CREATED_BY)
                                // .lastChgUserId(UPDATED_BY)
                                // .personRecordStatusCd("ACTIVE")
                                // .programJurisdictionOid(CLAYTON_STD_OID)
                                .build();
        }

        public static Investigation investigation_trichomoniasis(Long personId) {
                return Investigation.builder()
                                .id("Test_trichomoniasis")
                                // TODO .subjectEntityUid(personId)
                                // .classCd("PSN")
                                // .personCd("PAT")
                                // .caseTypeCd("I")
                                // .moodCd("EVN")
                                // .cdDescTxt("Trichomoniasis")
                                // .prog_area_cd("ARBO")
                                // .jurisdictionCd(DEKALB_CODE)
                                // .pregnantIndCd("Y")
                                // .actIdSeq(2)
                                // .actIdTypeCd("STATE")
                                // .rootExtensionTxt("StateRootExtensionText")
                                // .publicHealthCaseLocalId("CAS10001002GA01")
                                // .addUserId(CREATED_BY)
                                // .lastChgUserId(UPDATED_BY)
                                // .personRecordStatusCd("ACTIVE")
                                // .programJurisdictionOid(DEKALB_ARBO_OID)
                                // .notificationLocalId("notificationLocalId")
                                .build();
        }

        public static List<LabReport> labReport_acidFastStain(Long personId) {
                var now = Instant.now();
                return Arrays.asList(LabReport.builder()
                                .id("Test_acid-fast-stain")
                                .classCd("OBS")
                                .moodCd("EVN")
                                .programJurisdictionOid(CLAYTON_STD_OID)
                                .programAreaCd("STD")
                                .jurisdictionCd(CLAYTON_CODE)
                                .pregnantIndCd("Y")
                                .typeDescTxt("Filler Number")
                                .rootExtensionTxt("accession number")
                                .localId("OBS10003024GA01")
                                .activityToTime(now)
                                .effectiveFromTime(now)
                                .rptToStateTime(now)
                                .addTime(now)
                                .observationLastChgTime(now)
                                .electronicInd("E")
                                .addUserId(CREATED_BY)
                                .lastChange(now)
                                .lastChgUserId(UPDATED_BY)
                                .versionCtrlNbr(1L)
                                .observationRecordStatusCd("UNPROCESSED")
                                .typeCd("ORD")
                                .subjectClassCd("ORG")
                                .subjectEntityUid(personId)
                                .cdDescTxt("Acid-Fast Stain")
                                .displayName("abnormal")
                                .personCd("PAT")
                                .personRecordStatusCd("ACTIVE")
                                .build(),
                                // Mimics data for Ordering Provider entry
                                LabReport.builder()
                                                .id("Test_acid-fast-stain-ordering-provider")
                                                .classCd("OBS")
                                                .moodCd("EVN")
                                                .programAreaCd("STD")
                                                .programJurisdictionOid(CLAYTON_STD_OID)
                                                .typeCd("ORD")
                                                .subjectClassCd("PSN")
                                                .personCd("PAT")
                                                .personRecordStatusCd("ACTIVE")
                                                .subjectEntityUid(personId)
                                                .build(),
                                // Mimics data for Reporting Facility entry
                                LabReport.builder()
                                                .id("Test_acid-fast-stain-reporting-facility")
                                                .classCd("OBS")
                                                .moodCd("EVN")
                                                .programAreaCd("STD")
                                                .programJurisdictionOid(CLAYTON_STD_OID)
                                                .subjectClassCd("AUT")
                                                .personCd("PAT")
                                                .personRecordStatusCd("ACTIVE")
                                                .subjectEntityUid(personId)
                                                .build());
        }

        public static List<JurisdictionCode> getJurisdictionCodes() {
                // jurisdiction codes
                var jd1 = new JurisdictionCode();
                jd1.setId(DEKALB_CODE.toString());
                jd1.setNbsUid(DEKALB_CODE.intValue());
                jd1.setTypeCd("ALL");
                jd1.setAssigningAuthorityCd("GA");
                jd1.setAssigningAuthorityDescTxt("GA State");
                jd1.setCodeDescTxt("TEST-Dekalb County");
                jd1.setCodeShortDescTxt("TEST-Dekalb County");
                jd1.setIndentLevelNbr((short) 1);
                jd1.setIsModifiableInd('Y');
                jd1.setStateDomainCd("13");
                jd1.setCodeSetNm("S_JURDIC_C");
                jd1.setCodeSeqNum((short) 1);

                var jd2 = new JurisdictionCode();
                jd2.setId(CLAYTON_CODE.toString());
                jd2.setNbsUid(CLAYTON_CODE.intValue());
                jd2.setTypeCd("ALL");
                jd2.setAssigningAuthorityCd("GA");
                jd2.setAssigningAuthorityDescTxt("GA State");
                jd2.setCodeDescTxt("TEST-Clayton County");
                jd2.setCodeShortDescTxt("TEST-Clayton County");
                jd2.setIndentLevelNbr((short) 1);
                jd2.setIsModifiableInd('Y');
                jd2.setStateDomainCd("13");
                jd2.setCodeSetNm("S_JURDIC_C");
                jd2.setCodeSeqNum((short) 1);

                return Arrays.asList(jd1, jd2);
        }

}

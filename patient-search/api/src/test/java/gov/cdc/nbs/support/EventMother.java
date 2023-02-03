package gov.cdc.nbs.support;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import gov.cdc.nbs.entity.elasticsearch.ElasticsearchActId;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchObservation;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchOrganizationParticipation;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPersonParticipation;
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
                var participations = Arrays.asList(ElasticsearchPersonParticipation.builder()
                                .typeCd("SubjOfPHC")
                                .entityId(personId)
                                .personParentUid(personId)
                                .build());
                var actIds = Arrays.asList(ElasticsearchActId.builder()
                                .actIdSeq(2)
                                .typeCd("CITY")
                                .rootExtensionTxt("CityTypeRootExtensionText")
                                .build());
                return Investigation.builder()
                                .id("Test_bacterial_vaginosis")
                                .personParticipations(participations)
                                .investigationStatusCd("O")
                                .actIds(actIds)
                                .caseTypeCd("I")
                                .moodCd("EVN")
                                .cdDescTxt("Bacterial Vaginosis")
                                .progAreaCd("STD")
                                .jurisdictionCd(CLAYTON_CODE)
                                .pregnantIndCd("Y")
                                .localId("CAS10001000GA01")
                                .addUserId(CREATED_BY)
                                .lastChangeUserId(UPDATED_BY)
                                .programJurisdictionOid(CLAYTON_STD_OID)
                                .build();
        }

        public static Investigation investigation_trichomoniasis(Long personId) {
                var participations = Arrays.asList(ElasticsearchPersonParticipation.builder()
                                .typeCd("SubjOfPHC")
                                .entityId(personId)
                                .personParentUid(personId)
                                .build());
                var actIds = Arrays.asList(ElasticsearchActId.builder()
                                .actIdSeq(2)
                                .typeCd("STATE")
                                .rootExtensionTxt("StateRootExtensionText")
                                .build());
                return Investigation.builder()
                                .id("Test_trichomoniasis")
                                .personParticipations(participations)
                                .investigationStatusCd("O")
                                .actIds(actIds)
                                .caseTypeCd("I")
                                .moodCd("EVN")
                                .cdDescTxt("Trichomoniasis")
                                .progAreaCd("ARBO")
                                .jurisdictionCd(DEKALB_CODE)
                                .pregnantIndCd("Y")
                                .localId("CAS10001002GA01")
                                .addUserId(CREATED_BY)
                                .lastChangeUserId(UPDATED_BY)
                                .programJurisdictionOid(DEKALB_ARBO_OID)
                                .notificationLocalId("notificationLocalId")
                                .build();
        }

        public static Investigation investigation_trichomoniasis_closed(Long personId) {
                var participations = Arrays.asList(ElasticsearchPersonParticipation.builder()
                                .typeCd("SubjOfPHC")
                                .entityId(personId)
                                .personParentUid(personId)
                                .build());
                var actIds = Arrays.asList(ElasticsearchActId.builder()
                                .actIdSeq(2)
                                .typeCd("STATE")
                                .rootExtensionTxt("StateRootExtensionText")
                                .build());
                return Investigation.builder()
                                .id("Test_trichomoniasis-closed")
                                .personParticipations(participations)
                                .investigationStatusCd("C")
                                .actIds(actIds)
                                .caseTypeCd("I")
                                .moodCd("EVN")
                                .cdDescTxt("Trichomoniasis")
                                .progAreaCd("STD")
                                .jurisdictionCd(DEKALB_CODE)
                                .pregnantIndCd("Y")
                                .localId("CAS10001002GA01")
                                .addUserId(CREATED_BY)
                                .lastChangeUserId(UPDATED_BY)
                                .programJurisdictionOid(DEKALB_ARBO_OID)
                                .notificationLocalId("notificationLocalId")
                                .build();
        }

        public static LabReport labReport_acidFastStain(Long personId) {
                var now = Instant.now();
                var actIds = Arrays.asList(
                                ElasticsearchActId.builder()
                                                .actIdSeq(2)
                                                .typeDescTxt("Filler Number")
                                                .rootExtensionTxt("accession number")
                                                .build());
                var orgParticipations = Arrays.asList(
                                ElasticsearchOrganizationParticipation.builder()
                                                .typeCd("ORG")
                                                .subjectClassCd("ORG")
                                                .build(),
                                ElasticsearchOrganizationParticipation.builder()
                                                .typeCd("ORG")
                                                .subjectClassCd("AUT")
                                                .entityId(personId)
                                                .build());
                var personParticipations = Arrays.asList(
                                ElasticsearchPersonParticipation.builder()
                                                .entityId(personId)
                                                .personCd("PAT")
                                                .typeCd("PATSBJ")
                                                .personRecordStatus("ACTIVE")
                                                .personParentUid(personId)
                                                .build(),
                                ElasticsearchPersonParticipation.builder()
                                                .typeCd("ORG")
                                                .subjectClassCd("PSN")
                                                .personRecordStatus("ACTIVE")
                                                .entityId(personId)
                                                .build());
                var observations = Arrays.asList(
                                ElasticsearchObservation.builder()
                                                .cdDescTxt("Acid-Fast Stain")
                                                .displayName("abnormal")
                                                .build());
                return LabReport.builder()
                                .id("Test_acid-fast-stain")
                                .classCd("OBS")
                                .moodCd("EVN")
                                .programJurisdictionOid(CLAYTON_STD_OID)
                                .programAreaCd("STD")
                                .jurisdictionCd(CLAYTON_CODE)
                                .pregnantIndCd("Y")
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
                                .recordStatusCd("UNPROCESSED")
                                .actIds(actIds)
                                .organizationParticipations(orgParticipations)
                                .personParticipations(personParticipations)
                                .observations(observations)
                                .build();
        }

        public static LabReport labReport_acidFastStain_complete(Long personId) {
                var now = Instant.now();
                var actIds = Arrays.asList(
                                ElasticsearchActId.builder()
                                                .actIdSeq(2)
                                                .typeDescTxt("Filler Number")
                                                .rootExtensionTxt("accession number")
                                                .build());
                var orgParticipations = Arrays.asList(
                                ElasticsearchOrganizationParticipation.builder()
                                                .typeCd("ORG")
                                                .subjectClassCd("ORG")
                                                .build(),
                                ElasticsearchOrganizationParticipation.builder()
                                                .typeCd("ORG")
                                                .subjectClassCd("AUT")
                                                .entityId(personId)
                                                .build());
                var personParticipations = Arrays.asList(
                                ElasticsearchPersonParticipation.builder()
                                                .entityId(personId)
                                                .personCd("PAT")
                                                .typeCd("PATSBJ")
                                                .personRecordStatus("ACTIVE")
                                                .personParentUid(personId)
                                                .build(),
                                ElasticsearchPersonParticipation.builder()
                                                .typeCd("ORG")
                                                .subjectClassCd("PSN")
                                                .personRecordStatus("ACTIVE")
                                                .entityId(personId)
                                                .build());
                var observations = Arrays.asList(
                                ElasticsearchObservation.builder()
                                                .cdDescTxt("Acid-Fast Stain")
                                                .displayName("abnormal")
                                                .build());
                return LabReport.builder()
                                .id("Test_acid-fast-stain-complete")
                                .classCd("OBS")
                                .moodCd("EVN")
                                .programJurisdictionOid(CLAYTON_STD_OID)
                                .programAreaCd("STD")
                                .jurisdictionCd(CLAYTON_CODE)
                                .pregnantIndCd("Y")
                                .localId("OBS10003025GA01")
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
                                .recordStatusCd("PROCESSED")
                                .actIds(actIds)
                                .organizationParticipations(orgParticipations)
                                .personParticipations(personParticipations)
                                .observations(observations)
                                .build();
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

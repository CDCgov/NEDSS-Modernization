package gov.cdc.nbs.patient.profile.labreport;

import java.time.Instant;
import java.util.Collection;

record PatientLabReport(
                long patient,
                long observationUid,
                Instant lastChange,
                String classCd,
                String moodCd,
                Instant observationLastChgTime,
                String cdDescTxt,
                String recordStatusCd,
                String programAreaCd,
                long jurisdictionCd,
                String jurisdictionCodeDescTxt,
                String pregnantIndCd,
                String localId,
                Instant activityToTime,
                Instant effectiveFromTime,
                Instant rptToStateTime,
                Instant addTime,
                String electronicInd,
                long versionCtrlNbr,
                long addUserId,
                long lastChgUserId,
                Act act,
                Collection<PersonParticipation2> personParticipations,
                Collection<OrganizationParticipation2> organizationParticipations,
                Collection<MaterialParticipation2> materialParticipations,
                Collection<Observation2> observations,
                Collection<ActId2> actIds,
                Collection<AssociatedInvestigation2> associatedInvestigations) {
        record PersonParticipation2(
                        long actUid,
                        String localId,
                        String typeCd,
                        long entityId,
                        String subjectClassCd,
                        String participationRecordStatus,
                        String typeDescTxt,
                        Instant participationLastChangeTime,
                        String firstName,
                        String lastName,
                        Instant birthTime,
                        String currSexCd,
                        String personCd,
                        long personParentUid,
                        String personRecordStatus,
                        Instant personLastChangeTime,
                        long shortId) {
        }

        record OrganizationParticipation2(
                        long actUid,
                        String typeCd,
                        long entityId,
                        String subjectClassCd,
                        String typeDescTxt,
                        String participationRecordStatus,
                        Instant participationLastChangeTime,
                        String name,
                        Instant organizationLastChangeTime) {
        }

        record MaterialParticipation2(
                        long actUid,
                        String typeCd,
                        String entityId,
                        String subjectClassCd,
                        String typeDescTxt,
                        String participationRecordStatus,
                        Instant participationLastChangeTime,
                        String cd,
                        String cdDescTxt) {
        }

        record Observation2(
                        String cd,
                        String cdDescTxt,
                        String domainCd,
                        String statusCd,
                        String altCd,
                        String altDescTxt,
                        String altCdSystemCd,
                        String displayName,
                        String ovcCode,
                        String ovcAltCode,
                        String ovcAltDescTxt,
                        String ovcAltCdSystemCd) {
        }

        record ActId2(
                        long id,
                        String recordStatus,
                        long actIdSeq,
                        String rootExtensionTxt,
                        String typeCd,
                        Instant lastChangeTime) {
        }

        record AssociatedInvestigation2(
                        long publicHealthCaseUid,
                        String cdDescTxt,
                        String localId,
                        Instant lastChgTime,
                        Instant actRelationshipLastChgTime) {
        }

        record Act(
                        long id,
                        String classCd,
                        String moodCd) {
        }

}

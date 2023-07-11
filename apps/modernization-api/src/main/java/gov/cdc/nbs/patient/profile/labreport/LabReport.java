package gov.cdc.nbs.patient.profile.labreport;

import java.time.Instant;
import java.util.Collection;

record LabReport(
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
                Collection<PersonParticipation> personParticipations,
                Collection<OrganizationParticipation> organizationParticipations,
                Collection<MaterialParticipation> materialParticipations,
                Collection<Observation> observations,
                Collection<ActId> actIds,
                Collection<AssociatedInvestigation> associatedInvestigations) {
        record PersonParticipation(
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

        record OrganizationParticipation(
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

        record MaterialParticipation(
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

        record Observation(
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

        record ActId(
                        long id,
                        String recordStatus,
                        long actIdSeq,
                        String rootExtensionTxt,
                        String typeCd,
                        Instant lastChangeTime) {
        }

        record AssociatedInvestigation(
                        long publicHealthCaseUid,
                        String cdDescTxt,
                        String localId,
                        Instant lastChgTime,
                        Instant actRelationshipLastChgTime) {
        }
}
package gov.cdc.nbs.support;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Act;
import gov.cdc.nbs.entity.odse.ActId;
import gov.cdc.nbs.entity.odse.ActIdId;
import gov.cdc.nbs.entity.odse.ActRelationship;
import gov.cdc.nbs.entity.odse.ActRelationshipId;
import gov.cdc.nbs.entity.odse.Notification;
import gov.cdc.nbs.entity.odse.ObsValueCoded;
import gov.cdc.nbs.entity.odse.ObsValueCodedId;
import gov.cdc.nbs.entity.odse.Observation;
import gov.cdc.nbs.entity.odse.Organization;
import gov.cdc.nbs.entity.odse.Participation;
import gov.cdc.nbs.entity.odse.ParticipationId;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PublicHealthCase;
import gov.cdc.nbs.entity.srte.JurisdictionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Event {
        private List<Act> acts;
        private List<Organization> orgs;
        private List<Person> people;
        private List<JurisdictionCode> jurisdictionCodes;

        public Event(List<Act> acts, List<JurisdictionCode> jurisdictionCodes) {
            this.acts = acts;
            this.jurisdictionCodes = jurisdictionCodes;
            this.orgs = new ArrayList<>();
            this.people = new ArrayList<>();
        }
    }

    public static Event investigation_bacterialVaginosis(Long personId) {
        // act
        var act = new Act();
        act.setId(90000001L);
        act.setClassCd("CASE");
        act.setMoodCd("EVN");

        // actId
        var actId = new ActId();
        actId.setActUid(act);
        actId.setId(new ActIdId(act.getId(), (short) 2));
        actId.setTypeCd("CITY");
        actId.setStatusCd('A');
        actId.setStatusTime(Instant.now());
        actId.setRootExtensionTxt("CityTypeRootExtensionText");

        // participation - subject
        var pId = new ParticipationId(personId, act.getId(), "SubjOfPHC");
        var p = new Participation();
        p.setId(pId);
        p.setActUid(act);
        p.setActClassCd("CASE");
        p.setAddTime(Instant.now());
        p.setAddUserId(CREATED_BY);
        p.setLastChgTime(Instant.now());
        p.setLastChgUserId(UPDATED_BY);
        p.setRecordStatusCd(RecordStatus.ACTIVE);
        p.setStatusCd('A');
        p.setStatusTime(Instant.now());
        p.setSubjectClassCd("PSN");
        p.setTypeDescTxt("Subject Of Public Health Case");

        // public health case
        var phc = new PublicHealthCase();
        phc.setId(act.getId());
        phc.setAct(act);
        phc.setAddUserId(CREATED_BY);
        phc.setLastChgUserId(UPDATED_BY);
        phc.setCaseTypeCd('I');
        phc.setCd("419760006");
        phc.setLocalId("CAS10001000GA01");
        phc.setCdDescTxt("Bacterial Vaginosis");
        phc.setGroupCaseCnt((short) 1);
        phc.setInvestigationStatusCd("O");
        phc.setJurisdictionCd(CLAYTON_CODE.toString());
        phc.setProgAreaCd("STD");
        phc.setRecordStatusCd("OPEN");
        phc.setRecordStatusTime(Instant.now());
        phc.setStatusCd('A');
        phc.setProgramJurisdictionOid(CLAYTON_STD_OID);
        phc.setSharedInd('T');
        phc.setVersionCtrlNbr((short) 1);

        act.setParticipations(Arrays.asList(p));
        act.setPublicHealthCases(Arrays.asList(phc));
        act.setActIds(Arrays.asList(actId));
        return new Event(Arrays.asList(act), getJurisdictionCodes());
    }

    public static Event investigation_trichomoniasis(Long personId) {
        // act
        var act = new Act();
        act.setId(90000002L);
        act.setClassCd("CASE");
        act.setMoodCd("EVN");

        // actId
        var actId = new ActId();
        actId.setActUid(act);
        actId.setId(new ActIdId(act.getId(), (short) 1));
        actId.setTypeCd("STATE");
        actId.setStatusCd('A');
        actId.setStatusTime(Instant.now());
        actId.setRootExtensionTxt("StateRootExtensionText");

        // participation
        var participationId = new ParticipationId(personId, act.getId(), "SubjOfPHC");
        var participation = new Participation();
        participation.setId(participationId);
        participation.setActUid(act);
        participation.setActClassCd("CASE");
        participation.setAddTime(Instant.now());
        participation.setAddUserId(CREATED_BY);
        participation.setLastChgTime(Instant.now());
        participation.setLastChgUserId(UPDATED_BY);
        participation.setRecordStatusCd(RecordStatus.ACTIVE);
        participation.setStatusCd('A');
        participation.setStatusTime(Instant.now());
        participation.setSubjectClassCd("PSN");
        participation.setTypeDescTxt("Subject Of Public Health Case");

        // public health case
        var phc = new PublicHealthCase();
        phc.setId(act.getId());
        phc.setAct(act);
        phc.setAddUserId(CREATED_BY);
        phc.setLastChgUserId(UPDATED_BY);
        phc.setCaseTypeCd('I');
        phc.setCd("35089004");
        phc.setLocalId("CAS10001002GA01");
        phc.setCdDescTxt("Trichomoniasis");
        phc.setGroupCaseCnt((short) 1);
        phc.setInvestigationStatusCd("O");
        phc.setJurisdictionCd(DEKALB_CODE.toString());
        phc.setProgAreaCd("ARBO");
        phc.setRecordStatusCd("OPEN");
        phc.setPregnantIndCd("Y");
        phc.setRecordStatusTime(Instant.now());
        phc.setStatusCd('A');
        phc.setProgramJurisdictionOid(DEKALB_ARBO_OID);
        phc.setSharedInd('T');
        phc.setVersionCtrlNbr((short) 1);

        // notification
        var notification = new Notification();
        notification.setId(act.getId());
        notification.setAct(act);
        notification.setSharedInd('N');
        notification.setVersionCtrlNbr((short) 1);
        notification.setLocalId("notificationLocalId");

        act.setParticipations(Arrays.asList(participation));
        act.setPublicHealthCases(Arrays.asList(phc));
        act.setActIds(Arrays.asList(actId));
        act.setNotifications(Arrays.asList(notification));

        return new Event(Arrays.asList(act), getJurisdictionCodes());
    }

    public static Event labReport_acidFastStain(Long personId) {
        // org - ordering facility
        var org = ProviderMother.piedmontHospital();

        // person - ordering provider
        var johnX = ProviderMother.johnXerogeanes();

        // acts
        var act1 = new Act();
        act1.setId(90000003L);
        act1.setClassCd("OBS");
        act1.setMoodCd("EVN");

        var act2 = new Act();
        act2.setId(90000004L);
        act2.setClassCd("OBS");
        act2.setMoodCd("EVN");

        var act3 = new Act();
        act3.setId(90000005L);
        act3.setClassCd("OBS");
        act3.setMoodCd("EVN");

        var act4 = new Act();
        act4.setId(90000006L);
        act4.setClassCd("OBS");
        act4.setMoodCd("EVN");

        // actId
        var actId = new ActId();
        actId.setActUid(act1);
        actId.setId(new ActIdId(act1.getId(), (short) 0));
        actId.setTypeCd("FN");
        actId.setStatusCd('A');
        actId.setStatusTime(Instant.now());
        actId.setRootExtensionTxt("accession number");
        actId.setTypeDescTxt("Filler Number");
        actId.setRecordStatusCd("ACTIVE");
        actId.setAssigningAuthorityDescTxt("Default Manual Lab");

        // act relationship
        var ar1 = new ActRelationship();
        ar1.setId(new ActRelationshipId(act2.getId(), act1.getId(), "COMP"));
        ar1.setTargetActUid(act1);
        ar1.setSourceActUid(act2);
        ar1.setAddTime(Instant.now());
        ar1.setLastChgTime(Instant.now());
        ar1.setRecordStatusCd("ACTIVE");
        ar1.setRecordStatusTime(Instant.now());
        ar1.setSourceClassCd("OBS");
        ar1.setStatusCd('A');
        ar1.setStatusTime(Instant.now());
        ar1.setTargetClassCd("OBS");
        ar1.setTypeDescTxt("Has Component");

        var ar2 = new ActRelationship();
        ar2.setId(new ActRelationshipId(act3.getId(), act1.getId(), "APND"));
        ar2.setTargetActUid(act1);
        ar2.setSourceActUid(act3);
        ar2.setAddTime(Instant.now());
        ar2.setLastChgTime(Instant.now());
        ar2.setRecordStatusCd("ACTIVE");
        ar2.setRecordStatusTime(Instant.now());
        ar2.setSourceClassCd("OBS");
        ar2.setStatusCd('A');
        ar2.setStatusTime(Instant.now());
        ar2.setTargetClassCd("OBS");
        ar2.setTypeDescTxt("Appends");

        var ar3 = new ActRelationship();
        ar3.setId(new ActRelationshipId(act4.getId(), act1.getId(), "COMP"));
        ar3.setTargetActUid(act1);
        ar3.setSourceActUid(act4);
        ar3.setAddTime(Instant.now());
        ar3.setLastChgTime(Instant.now());
        ar3.setRecordStatusCd("ACTIVE");
        ar3.setRecordStatusTime(Instant.now());
        ar3.setSourceClassCd("OBS");
        ar3.setStatusCd('A');
        ar3.setStatusTime(Instant.now());
        ar3.setTargetClassCd("OBS");
        ar3.setTypeDescTxt("Has Component");

        // participation
        var p1 = new Participation();
        p1.setId(new ParticipationId(personId, act1.getId(), "PATSBJ"));
        p1.setActUid(act1);
        p1.setActClassCd("OBS");
        p1.setAddTime(Instant.now());
        p1.setAddUserId(CREATED_BY);
        p1.setLastChgTime(Instant.now());
        p1.setLastChgUserId(UPDATED_BY);
        p1.setRecordStatusCd(RecordStatus.ACTIVE);
        p1.setStatusCd('A');
        p1.setStatusTime(Instant.now());
        p1.setSubjectClassCd("PSN");
        p1.setTypeDescTxt("Patient subject");

        // participation - reporting facility
        var pId2 = new ParticipationId(org.getId(), act1.getId(), "AUT");
        var p2 = new Participation();
        p2.setId(pId2);
        p2.setActUid(act1);
        p2.setRecordStatusCd(RecordStatus.ACTIVE);
        p2.setStatusCd('A');
        p2.setSubjectClassCd("ORG");
        p2.setTypeDescTxt("Author");

        // participation - ordering facility
        var pId3 = new ParticipationId(org.getId(), act1.getId(), "ORD");
        var p3 = new Participation();
        p3.setId(pId3);
        p3.setActUid(act1);
        p3.setRecordStatusCd(RecordStatus.ACTIVE);
        p3.setStatusCd('A');
        p3.setSubjectClassCd("ORG");
        p3.setTypeDescTxt("Orderer");

        // participation - ordering provider
        var pId4 = new ParticipationId(johnX.getId(), act1.getId(), "ORD");
        var p4 = new Participation();
        p4.setId(pId4);
        p4.setActUid(act1);
        p4.setRecordStatusCd(RecordStatus.ACTIVE);
        p4.setStatusCd('A');
        p4.setSubjectClassCd("PSN");
        p4.setTypeDescTxt("Orderer");

        // Observation
        var obs = new Observation();
        obs.setId(act1.getId());
        obs.setAct(act1);
        obs.setAddReasonCd("ADD LAB REPORT");
        obs.setAddTime(Instant.now());
        obs.setAddUserId(CREATED_BY);
        obs.setCd("T-10130");
        obs.setCdDescTxt("Acid-Fast Stain");
        obs.setActivityToTime(Instant.now());
        obs.setEffectiveFromTime(Instant.now());
        obs.setRptToStateTime(Instant.now());
        obs.setCdSystemCd("DEFAULT");
        obs.setCdSystemDescTxt("Default Manual Lab");
        obs.setCtrlCdDisplayForm("LabReport");
        obs.setElectronicInd('E');
        obs.setJurisdictionCd(CLAYTON_CODE.toString());
        obs.setLastChgTime(Instant.now());
        obs.setLastChgUserId(UPDATED_BY);
        obs.setLocalId("OBS10003024GA01");
        obs.setObsDomainCdSt1("Order");
        obs.setProgAreaCd("STD");
        obs.setRecordStatusCd("UNPROCESSED");
        obs.setRecordStatusTime(Instant.now());
        obs.setStatusCd('D');
        obs.setStatusTime(Instant.now());
        obs.setTargetSiteCd("BE");
        obs.setPregnantIndCd("Y");
        obs.setTargetSiteDescTxt("Bilateral Ears");
        obs.setProgramJurisdictionOid(CLAYTON_STD_OID);
        obs.setSharedInd('T');
        obs.setVersionCtrlNbr((short) 1);

        var obs2 = new Observation();
        obs2.setId(act4.getId());
        obs2.setAct(act4);
        obs2.setAddReasonCd("ADD LAB REPORT");
        obs2.setCd("T-50130");
        obs2.setCdDescTxt("Acid-Fast Stain");
        obs2.setCdSystemCd("DEFAULT");
        obs2.setCdSystemDescTxt("Default Manual Lab");
        obs2.setCtrlCdDisplayForm("LabReport");
        obs2.setElectronicInd('N');
        obs2.setLocalId("OBS10003028GA01");
        obs2.setObsDomainCdSt1("Result");
        obs2.setStatusCd('D');
        obs2.setStatusTime(Instant.now());
        obs2.setProgramJurisdictionOid(4L);
        obs2.setSharedInd('T');
        obs2.setVersionCtrlNbr((short) 1);
        obs2.setAltCd("11545-1");
        obs2.setAltCdDescTxt("MICROSCOPIC OBSERVATION");

        // ObsValueCoded
        var ovc = new ObsValueCoded();
        ovc.setId(new ObsValueCodedId(act4.getId(), "ABN"));
        ovc.setObservationUid(obs2);
        ovc.setCodeSystemCd("NBS");
        ovc.setDisplayName("abnormal");
        ovc.setAltCd("R-42037");
        ovc.setAltCdDescTxt("SNOMED");
        ovc.setAltCdSystemCd("SNM");
        ovc.setAltCdSystemDescTxt("SNOMED");
        ovc.setCodeDerivedInd('Y');

        // set relationships
        obs2.setObsValueCodedList(Arrays.asList(ovc));

        act1.setParticipations(Arrays.asList(p1, p2, p3, p4));
        act1.setActIds(Arrays.asList(actId));
        act1.setObservations(Arrays.asList(obs));
        act2.setActRelationships(Arrays.asList(ar1));

        act3.setActRelationships(Arrays.asList(ar2));

        act4.setActRelationships(Arrays.asList(ar3));
        act4.setObservations(Arrays.asList(obs2));

        return new Event(
                Arrays.asList(act1, act2, act3, act4),
                Arrays.asList(org),
                Arrays.asList(johnX),
                getJurisdictionCodes());
    }

    private static List<JurisdictionCode> getJurisdictionCodes() {
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

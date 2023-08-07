package gov.cdc.nbs.patient.profile.report.lab;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Act;
import gov.cdc.nbs.entity.odse.Observation;
import gov.cdc.nbs.entity.odse.Participation;
import gov.cdc.nbs.entity.odse.ParticipationId;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.identity.TestUniqueIdGenerator;
import gov.cdc.nbs.support.util.RandomUtil;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class LabReportMother {

    private static final String LAB_REPORT_CLASS_CODE = "OBS";
    private static final String PERSON_CLASS = "PSN";
    private static final String SUBJECT_OF_LAB_REPORT = "ORD";
    private static final String LAB_REPORT_DISPLAY_FORM = "LabReport";
    private static final String LAB_REPORT_DOMAIN = "Order";

    private final MotherSettings settings;
    private final TestUniqueIdGenerator idGenerator;
    private final EntityManager entityManager;
    private final TestLabReportCleaner cleaner;

    private final TestLabReports reports;

    LabReportMother(
            final MotherSettings settings,
            final TestUniqueIdGenerator idGenerator,
            final EntityManager entityManager,
            final TestLabReportCleaner cleaner,
            final TestLabReports reports) {
        this.settings = settings;
        this.idGenerator = idGenerator;
        this.entityManager = entityManager;
        this.cleaner = cleaner;
        this.reports = reports;
    }

    public void reset() {
        this.cleaner.clean(this.settings.starting());
        this.reports.reset();
    }

    /**
     * Creates a Lab Report associated with the given {@code patient}
     *
     * @param patient The identifier of the patient.
     */
    void labReport(final long patient) {
        long identifier = idGenerator.next();
        String localId = idGenerator.nextLocal(LAB_REPORT_CLASS_CODE);

        Observation observation = new Observation(identifier, localId);
        observation.setActivityToTime(RandomUtil.getRandomDateInPast());
        observation.setCtrlCdDisplayForm(LAB_REPORT_DISPLAY_FORM);
        observation.setObsDomainCdSt1(LAB_REPORT_DOMAIN);

        // Condition: Flu activity code (Influenza)
        observation.setCd("10570");
        observation.setCdDescTxt("Condition");

        // Jurisdiction: Out of system
        observation.setJurisdictionCd("999999");

        observation.setRecordStatusCd("UNPROCESSED");
        observation.setRecordStatusTime(settings.createdOn());
        observation.setAddTime(settings.createdOn());
        observation.setAddUserId(settings.createdBy());

        subjectOfObservation(observation, patient);

        entityManager.persist(observation);

        this.reports.available(observation.getId());

    }

    public void unprocessedLabReport(final long patient) {
        // Observation
        long identifier = idGenerator.next();
        String localId = idGenerator.nextLocal(LAB_REPORT_CLASS_CODE);

        Observation observation = new Observation(identifier, localId);
        observation.setActivityToTime(RandomUtil.getRandomDateInPast());
        observation.setCtrlCdDisplayForm(LAB_REPORT_DISPLAY_FORM);

        // DOCUMENT.id.coalesce(OBSERVATION.id).as(ID),
        //                 OBSERVATION.ctrlCdDisplayForm.coalesce("Document").as(TYPE),
        //                 DOCUMENT.addTime.coalesce(OBSERVATION.addTime).as(DATE_RECEIVED),
        //                 DOCUMENT.addTime.coalesce(OBSERVATION.effectiveFromTime, OBSERVATION.activityToTime).as(EVENT_DATE),
        //                 DOCUMENT.localId.coalesce(OBSERVATION.localId).as(LOCAL_ID),
        //                 DOCUMENT.sendingFacilityNm,
        //                 DOCUMENT.externalVersionCtrlNbr,
        //                 OBSERVATION.electronicInd,
        //                 CONDITION.conditionDescTxt

        // .leftJoin(OBSERVATION).on(OBSERVATION.id.eq(PARTICIPATION.actUid.id))
        // .leftJoin(PARTICIPATION_2).on(PARTICIPATION_2.actUid.id.eq(OBSERVATION.id))
        // .leftJoin(ORGANIZATION).on(ORGANIZATION.id.eq(PARTICIPATION_2.id.subjectEntityUid))
        // .leftJoin(PERSON_2).on(PERSON_2.id.eq(PARTICIPATION_2.id.subjectEntityUid))
        // .leftJoin(RELATIONSHIP).on(RELATIONSHIP.targetActUid.id.eq(PARTICIPATION_2.actUid.id))
        // .leftJoin(OBS_VALUE_CODED).on(OBS_VALUE_CODED.id.observationUid.eq(RELATIONSHIP.sourceActUid.id))
        // .leftJoin(CONDITION).on(CONDITION.id.eq(DOCUMENT.cd).or(CONDITION.id.eq(OBSERVATION.cd)))

        // PARTICIPATION.id.typeCd.eq("PATSBJ")
        //         .and(PARTICIPATION.actClassCd.eq("OBS"))
        //         .and(PARTICIPATION.subjectClassCd.eq("PSN"))
        //         .and(PARTICIPATION.recordStatusCd.eq(RecordStatus.ACTIVE))
        //         .and(PARTICIPATION_2.id.typeCd.in("AUT", "SPC", "PATSBJ", "ORD"))
        //         .and(OBSERVATION.recordStatusCd.eq("UNPROCESSED"));
    }

    private void subjectOfObservation(final Observation observation, final long patient) {

        Act act = observation.getAct();

        // create the participation
        ParticipationId identifier = new ParticipationId(patient, observation.getId(), SUBJECT_OF_LAB_REPORT);

        Participation participation = new Participation();
        participation.setId(identifier);
        participation.setActClassCd(act.getClassCd());
        participation.setSubjectClassCd(PERSON_CLASS);

        participation.setRecordStatusCd(RecordStatus.ACTIVE);
        participation.setRecordStatusTime(settings.createdOn());
        participation.setAddTime(settings.createdOn());
        participation.setAddUserId(settings.createdBy());
        participation.setActUid(act);

        act.addParticipation(participation);

    }


}

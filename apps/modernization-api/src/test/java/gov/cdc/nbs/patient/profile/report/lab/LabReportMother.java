package gov.cdc.nbs.patient.profile.report.lab;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Act;
import gov.cdc.nbs.entity.odse.Observation;
import gov.cdc.nbs.entity.odse.Participation;
import gov.cdc.nbs.entity.odse.ParticipationId;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import gov.cdc.nbs.support.util.RandomUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;

@Component
@Transactional
public class LabReportMother {

    private static final String LAB_REPORT_CLASS_CODE = "OBS";
    private static final String PERSON_CLASS = "PSN";
    private static final String ORGANIZATION_CLASS = "ORG";
    private static final String SUBJECT_OF_LAB_REPORT = "ORD";
    private static final String REPORTER = "AUT";
    private static final String PATIENT_SUBJECT = "PATSBJ";
    private static final String LAB_REPORT_DISPLAY_FORM = "LabReport";
    private static final String LAB_REPORT_DOMAIN = "Order";

    private final MotherSettings settings;
    private final SequentialIdentityGenerator idGenerator;
    private final EntityManager entityManager;
    private final TestLabReportCleaner cleaner;

    private final TestLabReports reports;

    LabReportMother(
            final MotherSettings settings,
            final SequentialIdentityGenerator idGenerator,
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

    public void unprocessed(final long patient, final long organization) {
        // Observation
        long identifier = idGenerator.next();
        String localId = idGenerator.nextLocal(LAB_REPORT_CLASS_CODE);

        Observation observation = new Observation(identifier, localId);
        observation.setEffectiveFromTime(RandomUtil.getRandomDateInPast());
        observation.setActivityToTime(RandomUtil.getRandomDateInPast());
        observation.setCtrlCdDisplayForm(LAB_REPORT_DISPLAY_FORM);
        observation.setElectronicInd('N');
        observation.setRecordStatusCd("UNPROCESSED");
        observation.setProgramJurisdictionOid(1300200015L);

        patientSubjectParticipation(observation, patient);

        reportingFacility(observation, organization);

        entityManager.persist(observation);

        this.reports.available(observation.getId());
    }

    private void patientSubjectParticipation(final Observation observation, final long patient) {
        Act act = observation.getAct();

        // create the participation
        ParticipationId identifier = new ParticipationId(patient, observation.getId(), PATIENT_SUBJECT);

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

    private void reportingFacility(final Observation observation, final long organization) {
        Act act = observation.getAct();

        // create the participation
        ParticipationId identifier = new ParticipationId(organization, observation.getId(), REPORTER);

        Participation participation = new Participation();
        participation.setId(identifier);
        participation.setActClassCd(act.getClassCd());
        participation.setSubjectClassCd(ORGANIZATION_CLASS);
        participation.setActUid(act);


        act.addParticipation(participation);
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

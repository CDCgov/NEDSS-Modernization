package gov.cdc.nbs.event.report.lab;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Act;
import gov.cdc.nbs.entity.odse.Observation;
import gov.cdc.nbs.entity.odse.Participation;
import gov.cdc.nbs.entity.odse.ParticipationId;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Component
@Transactional
public class LabReportMother {

  private static final String LAB_REPORT_CLASS_CODE = "OBS";
  private static final String PERSON_CLASS = "PSN";
  private static final String ORGANIZATION_CLASS = "ORG";
  private static final String REPORTER = "AUT";
  private static final String PATIENT_SUBJECT = "PATSBJ";
  private static final String LAB_REPORT_DISPLAY_FORM = "LabReport";
  private static final String LAB_REPORT_DOMAIN = "Order";
  private static final long PIEDMONT_HOSPITAL = 10003001L;

  private final MotherSettings settings;
  private final SequentialIdentityGenerator idGenerator;
  private final EntityManager entityManager;
  private final TestLabReportCleaner cleaner;

  private final Active<LabReportIdentifier> report;
  private final Available<LabReportIdentifier> reports;

  private final PatientMother patientMother;

  LabReportMother(
      final MotherSettings settings,
      final SequentialIdentityGenerator idGenerator,
      final EntityManager entityManager,
      final TestLabReportCleaner cleaner,
      final Active<LabReportIdentifier> report,
      final Available<LabReportIdentifier> reports,
      final PatientMother patientMother
  ) {
    this.settings = settings;
    this.idGenerator = idGenerator;
    this.entityManager = entityManager;
    this.cleaner = cleaner;
    this.report = report;
    this.reports = reports;
    this.patientMother = patientMother;
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
  void labReport(final PatientIdentifier patient) {
    unprocessed(patient, PIEDMONT_HOSPITAL);
  }

  private void include(final LabReportIdentifier observation) {
    this.reports.available(observation);
    this.report.active(observation);
  }

  private void unprocessed(
      final PatientIdentifier patient,
      final long organization
  ) {
    PatientIdentifier revision = patientMother.revise(patient);
    // Observation
    long identifier = idGenerator.next();
    String localId = idGenerator.nextLocal(LAB_REPORT_CLASS_CODE);

    Observation observation = new Observation(identifier, localId);
    observation.setEffectiveFromTime(RandomUtil.getRandomDateInPast());
    observation.setActivityToTime(RandomUtil.getRandomDateInPast());
    observation.setCtrlCdDisplayForm(LAB_REPORT_DISPLAY_FORM);
    observation.setObsDomainCdSt1(LAB_REPORT_DOMAIN);
    observation.setElectronicInd('N');
    observation.setRecordStatusCd("UNPROCESSED");

    // Condition: Flu activity code (Influenza)
    observation.setCd("10570");
    observation.setCdDescTxt("Condition");

    // Jurisdiction: Out of system
    observation.setProgAreaCd("STD");
    observation.setJurisdictionCd("999999");
    observation.setProgramJurisdictionOid(1300200015L);   //  STD Out of System

    observation.setAddTime(settings.createdOn());
    observation.setAddUserId(settings.createdBy());
    observation.setLastChgTime(settings.createdOn());
    observation.setLastChgUserId(settings.createdBy());

    patientSubjectParticipation(observation, revision.id());

    reportingFacility(observation, organization);

    entityManager.persist(observation);

    include(new LabReportIdentifier(identifier));
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
    participation.setRecordStatusCd(RecordStatus.ACTIVE);
    participation.setRecordStatusTime(settings.createdOn());
    participation.setAddTime(settings.createdOn());
    participation.setAddUserId(settings.createdBy());
    participation.setActUid(act);


    act.addParticipation(participation);
  }

}

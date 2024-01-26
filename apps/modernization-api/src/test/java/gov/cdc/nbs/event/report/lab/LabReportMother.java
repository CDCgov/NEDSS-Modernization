package gov.cdc.nbs.event.report.lab;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Act;
import gov.cdc.nbs.entity.odse.Observation;
import gov.cdc.nbs.entity.odse.Participation;
import gov.cdc.nbs.entity.odse.ParticipationId;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.organization.OrganizationIdentifier;
import gov.cdc.nbs.support.provider.ProviderIdentifier;
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
  private static final String ORDERED_BY = "ORD";

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


  void create(final PatientIdentifier patient, final OrganizationIdentifier organization) {
    PatientIdentifier revision = patientMother.revise(patient);
    // Observation
    long identifier = idGenerator.next();
    String local = idGenerator.nextLocal(LAB_REPORT_CLASS_CODE);

    Observation observation = new Observation(identifier, local);
    observation.setEffectiveFromTime(RandomUtil.getRandomDateInPast());
    observation.setActivityToTime(RandomUtil.getRandomDateInPast());
    observation.setCtrlCdDisplayForm(LAB_REPORT_DISPLAY_FORM);
    observation.setObsDomainCdSt1(LAB_REPORT_DOMAIN);
    observation.setElectronicInd('N');
    observation.setRecordStatusCd("ACTIVE");

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

    forPatient(observation, revision.id());

    reportingFacility(observation, organization.identifier());

    entityManager.persist(observation);

    LabReportIdentifier created = new LabReportIdentifier(identifier, local);
    include(created);
  }

  private void forPatient(final Observation observation, final long patient) {
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

  private void include(final LabReportIdentifier identifier) {
    this.reports.available(identifier);
    this.report.active(identifier);
  }

  void unprocessed(final LabReportIdentifier identifier) {
    Observation lab = managed(identifier);
    lab.setRecordStatusCd("UNPROCESSED");
  }

  void electronic(final LabReportIdentifier identifier) {
    Observation lab = managed(identifier);
    lab.setElectronicInd('Y');
  }

  void orderedBy(final LabReportIdentifier identifier, final ProviderIdentifier provider) {
    Observation lab = managed(identifier);

    Act act = lab.getAct();

    // create the participation
    Participation participation = new Participation();
    participation.setId(new ParticipationId(provider.identifier(), lab.getId(), ORDERED_BY));
    participation.setActClassCd(act.getClassCd());
    participation.setSubjectClassCd(PERSON_CLASS);

    participation.setRecordStatusCd(RecordStatus.ACTIVE);
    participation.setRecordStatusTime(settings.createdOn());
    participation.setAddTime(settings.createdOn());
    participation.setAddUserId(settings.createdBy());
    participation.setActUid(act);

    act.addParticipation(participation);
  }

  private Observation managed(final LabReportIdentifier identifier) {
    return this.entityManager.find(Observation.class, identifier.identifier());
  }


}

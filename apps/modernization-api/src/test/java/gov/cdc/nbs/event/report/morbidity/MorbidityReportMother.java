package gov.cdc.nbs.event.report.morbidity;

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

import jakarta.persistence.EntityManager;

import java.time.ZoneOffset;

@Component
@Transactional
public class MorbidityReportMother {

  private static final String MORBIDITY_CLASS_CODE = "OBS";
  private static final String PERSON_CLASS = "PSN";
  private static final String SUBJECT_OF_MORBIDITY = "SubjOfMorbReport";
  private static final String MORBIDITY_DISPLAY_FORM = "MorbReport";
  private static final String MORBIDITY_DOMAIN = "Order";
  private static final String REPORTER = "ReporterOfMorbReport";
  private static final String ORGANIZATION_CLASS = "ORG";
  private static final String ORDERED_BY = "PhysicianOfMorb";

  private final MotherSettings settings;
  private final SequentialIdentityGenerator idGenerator;
  private final EntityManager entityManager;
  private final TestMorbidityCleaner cleaner;
  private final Available<MorbidityReportIdentifier> available;
  private final Active<MorbidityReportIdentifier> active;
  private final PatientMother patientMother;

  MorbidityReportMother(
      final MotherSettings settings,
      final SequentialIdentityGenerator idGenerator,
      final EntityManager entityManager,
      final TestMorbidityCleaner cleaner,
      final Available<MorbidityReportIdentifier> available,
      final Active<MorbidityReportIdentifier> active,
      final PatientMother patientMother
  ) {
    this.settings = settings;
    this.idGenerator = idGenerator;
    this.entityManager = entityManager;
    this.cleaner = cleaner;
    this.available = available;
    this.active = active;
    this.patientMother = patientMother;
  }

  void reset() {
    this.cleaner.clean(this.settings.starting());
    this.active.reset();
    this.available.reset();
  }

  /**
   * Creates a Morbidity Report associated with the given {@code patient}
   *
   * @param patient The identifier of the patient.
   */
  void create(final PatientIdentifier patient, final OrganizationIdentifier organization) {
    PatientIdentifier revision = patientMother.revise(patient);
    long identifier = idGenerator.next();
    String localId = idGenerator.nextLocal(MORBIDITY_CLASS_CODE);

    Observation observation = new Observation(identifier, localId);
    observation.setActivityToTime(RandomUtil.getRandomDateInPast());
    observation.setCtrlCdDisplayForm(MORBIDITY_DISPLAY_FORM);
    observation.setObsDomainCdSt1(MORBIDITY_DOMAIN);

    // Condition: Flu activity code (Influenza)
    observation.setCd("10570");
    observation.setCdDescTxt("Condition");

    // Jurisdiction: Out of system
    observation.setProgAreaCd("STD");
    observation.setJurisdictionCd("999999");
    observation.setProgramJurisdictionOid(1300200015L);

    observation.setRecordStatusCd("ACTIVE");
    observation.setRecordStatusTime(settings.createdOn().toInstant(ZoneOffset.UTC));
    observation.setAddTime(settings.createdOn().toInstant(ZoneOffset.UTC));
    observation.setAddUserId(settings.createdBy());

    forPatient(observation, revision.id());

    reportedBy(observation, organization.identifier());

    entityManager.persist(observation);

    MorbidityReportIdentifier created = new MorbidityReportIdentifier(identifier, localId);

    include(created);

  }

  private void include(final MorbidityReportIdentifier identifier) {
    this.available.available(identifier);
    this.active.active(identifier);
  }

  void unprocessed(final MorbidityReportIdentifier identifier) {
    Observation lab = managed(identifier);
    lab.setRecordStatusCd("UNPROCESSED");
  }

  private void forPatient(final Observation observation, final long patient) {
    Act act = observation.getAct();

    // create the participation
    ParticipationId identifier = new ParticipationId(patient, observation.getId(), SUBJECT_OF_MORBIDITY);

    Participation participation = new Participation();
    participation.setId(identifier);
    participation.setActClassCd(act.getClassCd());
    participation.setSubjectClassCd(PERSON_CLASS);

    participation.setRecordStatusCd(RecordStatus.ACTIVE);
    participation.setRecordStatusTime(settings.createdOn().toInstant(ZoneOffset.UTC));
    participation.setAddTime(settings.createdOn().toInstant(ZoneOffset.UTC));
    participation.setAddUserId(settings.createdBy());
    participation.setActUid(act);

    act.addParticipation(participation);
  }

  private void reportedBy(final Observation observation, final long organization) {
    Act act = observation.getAct();

    // create the participation
    ParticipationId identifier = new ParticipationId(organization, observation.getId(), REPORTER);

    Participation participation = new Participation();
    participation.setId(identifier);
    participation.setActClassCd(act.getClassCd());
    participation.setSubjectClassCd(ORGANIZATION_CLASS);
    participation.setRecordStatusCd(RecordStatus.ACTIVE);
    participation.setRecordStatusTime(settings.createdOn().toInstant(ZoneOffset.UTC));
    participation.setAddTime(settings.createdOn().toInstant(ZoneOffset.UTC));
    participation.setAddUserId(settings.createdBy());
    participation.setActUid(act);

    act.addParticipation(participation);
  }

  void orderedBy(final MorbidityReportIdentifier identifier, final ProviderIdentifier provider) {
    Observation lab = managed(identifier);

    Act act = lab.getAct();

    // create the participation
    Participation participation = new Participation();
    participation.setId(new ParticipationId(provider.identifier(), lab.getId(), ORDERED_BY));
    participation.setActClassCd(act.getClassCd());
    participation.setSubjectClassCd(PERSON_CLASS);

    participation.setRecordStatusCd(RecordStatus.ACTIVE);
    participation.setRecordStatusTime(settings.createdOn().toInstant(ZoneOffset.UTC));
    participation.setAddTime(settings.createdOn().toInstant(ZoneOffset.UTC));
    participation.setAddUserId(settings.createdBy());
    participation.setActUid(act);

    act.addParticipation(participation);
  }

  private Observation managed(final MorbidityReportIdentifier identifier) {
    return this.entityManager.find(Observation.class, identifier.identifier());
  }
}

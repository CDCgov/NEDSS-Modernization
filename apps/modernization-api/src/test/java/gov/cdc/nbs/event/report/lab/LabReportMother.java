package gov.cdc.nbs.event.report.lab;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.*;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.organization.OrganizationIdentifier;
import gov.cdc.nbs.support.provider.ProviderIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.authorization.jurisdiction.JurisdictionIdentifier;
import gov.cdc.nbs.testing.authorization.programarea.ProgramAreaIdentifier;
import gov.cdc.nbs.testing.data.TestingDataCleaner;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import gov.cdc.nbs.testing.patient.RevisionMother;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
@ScenarioScope
@Transactional
public class LabReportMother {

  private static final String DELETE_IN = """
      delete from Participation where act_class_cd = 'OBS' and act_uid in (:identifiers);
      delete from Observation where observation_uid in (:identifiers);
      delete from Act where class_cd = 'OBS' and act_uid in (:identifiers);
      """;

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

  private final JdbcClient client;
  private final TestingDataCleaner<Long> cleaner;
  private final Active<AccessionIdentifier> activeAccessionIdentifier;
  private final Active<LabReportIdentifier> active;
  private final Available<LabReportIdentifier> available;

  private final RevisionMother revisionMother;

  LabReportMother(
      final MotherSettings settings,
      final SequentialIdentityGenerator idGenerator,
      final EntityManager entityManager,
      final JdbcClient client,
      final Active<LabReportIdentifier> active,
      final Available<LabReportIdentifier> available,
      final Active<AccessionIdentifier> activeAccessionIdentifier,
      final RevisionMother revisionMother
  ) {
    this.settings = settings;
    this.idGenerator = idGenerator;
    this.entityManager = entityManager;
    this.client = client;
    this.active = active;
    this.available = available;
    this.activeAccessionIdentifier = activeAccessionIdentifier;
    this.revisionMother = revisionMother;

    this.cleaner = new TestingDataCleaner<>(client, DELETE_IN, "identifiers");
  }

  @PreDestroy
  public void reset() {
    this.cleaner.clean();
  }

  void create(
      final PatientIdentifier patient,
      final OrganizationIdentifier organization,
      final JurisdictionIdentifier jurisdiction,
      final ProgramAreaIdentifier programArea) {
    PatientIdentifier revision = revisionMother.revise(patient);
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

    within(observation, programArea, jurisdiction);

    observation.setAddTime(settings.createdOn().toInstant(ZoneOffset.UTC));
    observation.setAddUserId(settings.createdBy());
    observation.setLastChgTime(settings.createdOn().toInstant(ZoneOffset.UTC));
    observation.setLastChgUserId(settings.createdBy());

    forPatient(observation, revision.id());

    reportedBy(observation, organization.identifier());

    entityManager.persist(observation);

    include(new LabReportIdentifier(identifier, local));
  }

  private void within(
      final Observation observation,
      final ProgramAreaIdentifier programArea,
      final JurisdictionIdentifier jurisdiction) {
    observation.setProgAreaCd(programArea.code());
    observation.setJurisdictionCd(jurisdiction.code());
    observation.setProgramJurisdictionOid(programArea.oid(jurisdiction));
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

  private void include(final LabReportIdentifier identifier) {
    this.cleaner.include(identifier.identifier());

    this.available.available(identifier);
    this.active.active(identifier);
  }

  void within(
      final LabReportIdentifier report,
      final ProgramAreaIdentifier programArea,
      final JurisdictionIdentifier jurisdiction
  ) {
    this.client.sql(
            "update Observation set prog_area_cd = ?, jurisdiction_cd = ?, program_jurisdiction_oid = ? where observation_uid = ?")
        .param(programArea.code())
        .param(jurisdiction.code())
        .param(programArea.oid(jurisdiction))
        .param(report.identifier())
        .update();
  }

  void unprocessed(final LabReportIdentifier report) {
    this.client.sql("update Observation set record_status_cd = 'UNPROCESSED' where observation_uid = ?")
        .param(report.identifier())
        .update();
  }

  void electronic(final LabReportIdentifier report) {
    this.client.sql("update Observation set electronic_ind = 'Y' where observation_uid = ?")
        .param(report.identifier())
        .update();
  }

  void enteredExternally(final LabReportIdentifier report) {
    this.client.sql("update Observation set electronic_ind = 'E' where observation_uid = ?")
        .param(report.identifier())
        .update();
  }

  void orderedBy(final LabReportIdentifier lab, final OrganizationIdentifier organization) {
    Observation observation = managed(lab);
    Act act = observation.getAct();

    // create the participation
    ParticipationId identifier = new ParticipationId(organization.identifier(), observation.getId(), ORDERED_BY);

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

  void orderedBy(final LabReportIdentifier identifier, final ProviderIdentifier provider) {
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

  void filledBy(final LabReportIdentifier identifier, final String number) {
    Observation lab = managed(identifier);

    Act act = lab.getAct();

    // need an id and seq
    ActId filler = new ActId(new ActIdId(act.getId(), act.getActIds().size()));
    filler.setTypeCd("FN");
    filler.setTypeDescTxt("Filler Number");
    filler.setRootExtensionTxt(number);

    act.addIdentifier(filler);
    activeAccessionIdentifier.active(new AccessionIdentifier(act.getId(), number));
  }

  void forPregnantPatient(final LabReportIdentifier report) {
    this.client.sql("update Observation set pregnant_ind_cd = 'Y' where observation_uid = ?")
        .param(report.identifier())
        .update();
  }

  void receivedOn(final LabReportIdentifier report, final LocalDateTime on) {
    this.client.sql("update Observation set add_time = ? where observation_uid = ?")
        .param(on)
        .param(report.identifier())
        .update();
  }

  void collectedOn(final LabReportIdentifier report, final LocalDate on) {
    this.client.sql("update Observation set effective_from_time = ? where observation_uid = ?")
        .param(on)
        .param(report.identifier())
        .update();
  }

  void created(
      final LabReportIdentifier report,
      final long by,
      final Instant on
  ) {
    this.client.sql("update Observation set add_user_id = ?, add_time = ? where observation_uid = ?")
        .param(by)
        .param(on)
        .param(report.identifier())
        .update();
  }

  void updated(
      final LabReportIdentifier identifier,
      final long by,
      final Instant on
  ) {
    Observation lab = managed(identifier);
    lab.setLastChgUserId(by);
    lab.setLastChgTime(on);
    lab.setVersionCtrlNbr((short) (lab.getVersionCtrlNbr() + 1));
  }

  private Observation managed(final LabReportIdentifier identifier) {
    return this.entityManager.find(Observation.class, identifier.identifier());
  }


}

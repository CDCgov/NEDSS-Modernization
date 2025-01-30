package gov.cdc.nbs.event.report.lab;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Act;
import gov.cdc.nbs.entity.odse.ActId;
import gov.cdc.nbs.entity.odse.ActIdId;
import gov.cdc.nbs.entity.odse.Observation;
import gov.cdc.nbs.entity.odse.Participation;
import gov.cdc.nbs.entity.odse.ParticipationId;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.jurisdiction.JurisdictionIdentifier;
import gov.cdc.nbs.support.organization.OrganizationIdentifier;
import gov.cdc.nbs.support.programarea.ProgramAreaIdentifier;
import gov.cdc.nbs.support.provider.ProviderIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneOffset;

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
  private final Active<AccessionIdentifier> activeAccessionIdentifier;
  private final Active<LabReportIdentifier> active;
  private final Available<LabReportIdentifier> available;

  private final PatientMother patientMother;

  LabReportMother(
      final MotherSettings settings,
      final SequentialIdentityGenerator idGenerator,
      final EntityManager entityManager,
      final TestLabReportCleaner cleaner,
      final Active<LabReportIdentifier> active,
      final Available<LabReportIdentifier> available,
      final Active<AccessionIdentifier> activeAccessionIdentifier,
      final PatientMother patientMother) {
    this.settings = settings;
    this.idGenerator = idGenerator;
    this.entityManager = entityManager;
    this.cleaner = cleaner;
    this.active = active;
    this.available = available;
    this.activeAccessionIdentifier = activeAccessionIdentifier;
    this.patientMother = patientMother;
  }

  public void reset() {
    this.cleaner.clean(this.settings.starting());
    this.available.reset();
  }

  void create(
      final PatientIdentifier patient,
      final OrganizationIdentifier organization,
      final JurisdictionIdentifier jurisdiction,
      final ProgramAreaIdentifier programArea) {
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

    within(observation, programArea, jurisdiction);

    observation.setAddTime(settings.createdOn().toInstant(ZoneOffset.UTC));
    observation.setAddUserId(settings.createdBy());
    observation.setLastChgTime(settings.createdOn().toInstant(ZoneOffset.UTC));
    observation.setLastChgUserId(settings.createdBy());

    forPatient(observation, revision.id());

    reportedBy(observation, organization.identifier());

    entityManager.persist(observation);

    LabReportIdentifier created = new LabReportIdentifier(identifier, local);
    include(created);
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
    this.available.available(identifier);
    this.active.active(identifier);
  }

  void within(
      final LabReportIdentifier identifier,
      final ProgramAreaIdentifier programArea,
      final JurisdictionIdentifier jurisdiction) {
    Observation lab = managed(identifier);
    within(lab, programArea, jurisdiction);
  }

  void unprocessed(final LabReportIdentifier identifier) {
    Observation lab = managed(identifier);
    lab.setRecordStatusCd("UNPROCESSED");
  }

  void electronic(final LabReportIdentifier identifier) {
    Observation lab = managed(identifier);
    lab.setElectronicInd('Y');
  }

  void enteredExternally(final LabReportIdentifier identifier) {
    Observation lab = managed(identifier);
    lab.setElectronicInd('E');
  }

  void orderedBy(final LabReportIdentifier lab, final long organization) {
    Observation observation = managed(lab);
    Act act = observation.getAct();

    // create the participation
    ParticipationId identifier = new ParticipationId(organization, observation.getId(), ORDERED_BY);

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

  void forPregnantPatient(final LabReportIdentifier identifier) {
    Observation lab = managed(identifier);
    lab.setPregnantIndCd("Y");
  }

  void receivedOn(final LabReportIdentifier identifier, final Instant date) {
    Observation lab = managed(identifier);
    lab.setRptToStateTime(date);
  }

  void created(
      final LabReportIdentifier identifier,
      final long by,
      final Instant on) {
    Observation lab = managed(identifier);
    lab.setAddTime(on);
    lab.setAddUserId(by);
  }

  void updated(
      final LabReportIdentifier identifier,
      final long by,
      final Instant on) {
    Observation lab = managed(identifier);
    lab.setLastChgUserId(by);
    lab.setLastChgTime(on);
    lab.setVersionCtrlNbr((short) (lab.getVersionCtrlNbr() + 1));
  }

  private Observation managed(final LabReportIdentifier identifier) {
    return this.entityManager.find(Observation.class, identifier.identifier());
  }


}

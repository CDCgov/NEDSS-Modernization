package gov.cdc.nbs.event.investigation;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Act;
import gov.cdc.nbs.entity.odse.ActId;
import gov.cdc.nbs.entity.odse.ActIdId;
import gov.cdc.nbs.entity.odse.Participation;
import gov.cdc.nbs.entity.odse.ParticipationId;
import gov.cdc.nbs.entity.odse.PublicHealthCase;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.jurisdiction.JurisdictionIdentifier;
import gov.cdc.nbs.support.programarea.ProgramAreaIdentifier;
import gov.cdc.nbs.support.provider.ProviderIdentifier;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.time.Instant;

@Component
public class InvestigationMother {

  private static final String INVESTIGATION_CODE = "CAS";
  private static final String PERSON_CODE = "PSN";
  private static final String INVESTIGATION_CLASS = "CASE";
  private final SequentialIdentityGenerator idGenerator;
  private final MotherSettings settings;
  private final EntityManager entityManager;
  private final TestInvestigations investigations;

  private final Available<InvestigationIdentifier> available;
  private final Active<InvestigationIdentifier> active;
  private final PatientMother patientMother;
  private final TestInvestigationCleaner cleaner;

  InvestigationMother(
      final MotherSettings settings,
      final SequentialIdentityGenerator idGenerator,
      final EntityManager entityManager,
      final TestInvestigations investigations,
      final Available<InvestigationIdentifier> available,
      final Active<InvestigationIdentifier> active,
      final PatientMother patientMother,
      final TestInvestigationCleaner cleaner
  ) {
    this.idGenerator = idGenerator;
    this.settings = settings;
    this.entityManager = entityManager;
    this.investigations = investigations;
    this.available = available;
    this.active = active;
    this.patientMother = patientMother;
    this.cleaner = cleaner;
  }

  void reset() {
    this.cleaner.clean(this.settings.starting());
    this.investigations.reset();
  }

  public PublicHealthCase investigation(final long subject) {
    long identifier = idGenerator.next();
    String local = idGenerator.nextLocal(INVESTIGATION_CODE);

    PublicHealthCase investigation = new PublicHealthCase(
        identifier,
        local,
        settings.createdBy(),
        settings.createdOn()
    );

    investigation.within(
        // out of system
        "999999",

        "STD",
        // Clayton county + STD
        1300600015L
    );

    investigation.condition("42060"); // other injury

    subjectOf(investigation.act(), subject);

    this.entityManager.persist(investigation);

    include(new InvestigationIdentifier(identifier, local));

    return investigation;
  }

  void create(
      final PatientIdentifier patient,
      final JurisdictionIdentifier jurisdiction,
      final ProgramAreaIdentifier programArea
  ) {
    PatientIdentifier revision = patientMother.revise(patient);

    long identifier = idGenerator.next();
    String local = idGenerator.nextLocal(INVESTIGATION_CODE);

    PublicHealthCase investigation = new PublicHealthCase(
        identifier,
        local,
        settings.createdBy(),
        settings.createdOn()
    );

    investigation.condition("42060"); // other injury

    investigation.within(
        jurisdiction.code(),
        programArea.code(),
        programArea.oid(jurisdiction)
    );

    subjectOf(investigation.act(), revision.id());

    this.entityManager.persist(investigation);

    include(new InvestigationIdentifier(identifier, local));
  }

  private void include(final InvestigationIdentifier investigation) {
    this.investigations.available(investigation.identifier());
    this.available.available(investigation);
    this.active.active(investigation);
  }

  private void subjectOf(final Act act, final long patient) {

    // create the participation
    ParticipationId identifier = new ParticipationId(patient, act.getId(), "SubjOfPHC");

    Participation participation = new Participation();
    participation.setId(identifier);
    participation.setActClassCd(INVESTIGATION_CLASS);
    participation.setSubjectClassCd(PERSON_CODE);

    participation.setRecordStatusCd(RecordStatus.ACTIVE);
    participation.setRecordStatusTime(settings.createdOn());
    participation.setAddTime(settings.createdOn());
    participation.setAddUserId(settings.createdBy());
    participation.setActUid(act);

    act.addParticipation(participation);

  }

  void within(
      final InvestigationIdentifier identifier,
      final ProgramAreaIdentifier programArea,
      final JurisdictionIdentifier jurisdiction
  ) {
    PublicHealthCase investigation = managed(identifier);
    investigation.within(
        jurisdiction.code(),
        programArea.code(),
        programArea.oid(jurisdiction)
    );
  }

  void closed(
      final InvestigationIdentifier identifier,
      final Instant on
  ) {
    PublicHealthCase investigation = managed(identifier);
    investigation.close(on);
  }

  void processing(final InvestigationIdentifier identifier, final String status) {
    PublicHealthCase investigation = managed(identifier);
    investigation.processingStatus(status);
  }

  void caseStatus(final InvestigationIdentifier identifier, final String status) {
    PublicHealthCase investigation = managed(identifier);
    investigation.caseStatus(status);
  }

  void created(
      final InvestigationIdentifier identifier,
      final long by,
      final Instant on
  ) {
    PublicHealthCase investigation = managed(identifier);
    investigation.created(by, on);
  }

  void updated(
      final InvestigationIdentifier identifier,
      final long by,
      final Instant on
  ) {
    PublicHealthCase investigation = managed(identifier);
    investigation.updated(by, on);
  }

  void forPregnantPatient(final InvestigationIdentifier identifier) {
    PublicHealthCase investigation = managed(identifier);
    investigation.pregnancyStatus("Y");
  }

  void forNonPregnantPatient(final InvestigationIdentifier identifier) {
    PublicHealthCase investigation = managed(identifier);
    investigation.pregnancyStatus("N");
  }

  void forPregnancyUnknownPatient(final InvestigationIdentifier identifier) {
    PublicHealthCase investigation = managed(identifier);
    investigation.pregnancyStatus("UNK");
  }

  void withCondition(final InvestigationIdentifier identifier, final String condition) {
    PublicHealthCase investigation = managed(identifier);
    investigation.condition(condition);
  }

  void started(final InvestigationIdentifier identifier, final Instant on) {
    PublicHealthCase investigation = managed(identifier);
    investigation.started(on);
  }

  void reported(final InvestigationIdentifier identifier, final Instant on) {
    PublicHealthCase investigation = managed(identifier);
    investigation.reported(on);
  }

  void relatedToABCSCase(
      final InvestigationIdentifier identifier,
      final String number
  ) {
    PublicHealthCase investigation = managed(identifier);

    Act act = investigation.act();

    ActId relatedTo = new ActId(new ActIdId(act.getId(), 2));
    relatedTo.setTypeCd("STATE");
    relatedTo.setRootExtensionTxt(number);

    act.addIdentifier(relatedTo);

  }

  void relatedToCountyCase(
      final InvestigationIdentifier identifier,
      final String number
  ) {
    PublicHealthCase investigation = managed(identifier);

    Act act = investigation.act();

    ActId relatedTo = new ActId(new ActIdId(act.getId(), 2));
    relatedTo.setTypeCd("CITY");
    relatedTo.setRootExtensionTxt(number);

    act.addIdentifier(relatedTo);

  }

  void relatedToStateCase(
      final InvestigationIdentifier identifier,
      final String number
  ) {
    PublicHealthCase investigation = managed(identifier);

    Act act = investigation.act();

    ActId relatedTo = new ActId(new ActIdId(act.getId(), 1));
    relatedTo.setTypeCd("STATE");
    relatedTo.setRootExtensionTxt(number);

    act.addIdentifier(relatedTo);

  }

  void relatedToOutbreak(
      final InvestigationIdentifier identifier,
      final String outbreak
  ) {
    PublicHealthCase investigation = managed(identifier);
    investigation.outbreak(outbreak);
  }

  void reportedBy(final InvestigationIdentifier identifier, final long organization) {
    PublicHealthCase investigation = managed(identifier);
    Act act = investigation.act();


    Participation participation = new Participation();
    participation.setId(new ParticipationId(organization, identifier.identifier(), "OrgAsReporterOfPHC"));
    participation.setActClassCd(act.getClassCd());
    participation.setSubjectClassCd("ORG");
    participation.setRecordStatusCd(RecordStatus.ACTIVE);
    participation.setRecordStatusTime(settings.createdOn());
    participation.setAddTime(settings.createdOn());
    participation.setAddUserId(settings.createdBy());
    participation.setActUid(act);


    act.addParticipation(participation);
  }

  void reportedBy(
      final InvestigationIdentifier identifier,
      final ProviderIdentifier provider
  ) {
    PublicHealthCase investigation = managed(identifier);

    Act act = investigation.act();

    Participation participation = new Participation();
    participation.setId(
        new ParticipationId(
            provider.identifier(),
            identifier.identifier(),
            "PerAsReporterOfPHC"
        )
    );
    participation.setActClassCd(act.getClassCd());
    participation.setSubjectClassCd("PSN");

    participation.setRecordStatusCd(RecordStatus.ACTIVE);
    participation.setRecordStatusTime(settings.createdOn());
    participation.setAddTime(settings.createdOn());
    participation.setAddUserId(settings.createdBy());
    participation.setActUid(act);

    act.addParticipation(participation);

  }
  void investigatedBy(final InvestigationIdentifier identifier, final ProviderIdentifier investigator) {
    PublicHealthCase investigation = managed(identifier);

    Act act = investigation.act();

    Participation participation = new Participation();
    participation.setId(new ParticipationId(investigator.identifier(), identifier.identifier(), "InvestgrOfPHC"));
    participation.setActClassCd(act.getClassCd());
    participation.setSubjectClassCd("PSN");

    participation.setRecordStatusCd(RecordStatus.ACTIVE);
    participation.setRecordStatusTime(settings.createdOn());
    participation.setAddTime(settings.createdOn());
    participation.setAddUserId(settings.createdBy());
    participation.setActUid(act);

    act.addParticipation(participation);

  }

  private PublicHealthCase managed(final InvestigationIdentifier identifier) {
    return this.entityManager.find(PublicHealthCase.class, identifier.identifier());
  }



}

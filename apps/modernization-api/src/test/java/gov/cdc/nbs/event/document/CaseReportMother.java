package gov.cdc.nbs.event.document;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Act;
import gov.cdc.nbs.entity.odse.NbsDocument;
import gov.cdc.nbs.entity.odse.NbsDocumentMetadatum;
import gov.cdc.nbs.entity.odse.Participation;
import gov.cdc.nbs.entity.odse.ParticipationId;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
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
public class CaseReportMother {

  private static final String DOCUMENT_CLASS = "DOC";
  private static final String PERSON_CLASS = "PSN";
  private final MotherSettings settings;
  private final SequentialIdentityGenerator idGenerator;
  private final EntityManager entityManager;
  private final TestDocumentCleaner cleaner;

  private final Active<CaseReportIdentifier> active;
  private final Available<CaseReportIdentifier> available;
  private final PatientMother patientMother;

  CaseReportMother(
      final MotherSettings settings,
      final SequentialIdentityGenerator idGenerator,
      final EntityManager entityManager,
      final TestDocumentCleaner cleaner,
      final Active<CaseReportIdentifier> active,
      final Available<CaseReportIdentifier> available,
      final PatientMother patientMother
  ) {
    this.settings = settings;
    this.idGenerator = idGenerator;
    this.entityManager = entityManager;
    this.cleaner = cleaner;
    this.active = active;
    this.available = available;
    this.patientMother = patientMother;
  }

  void reset() {
    this.cleaner.clean(this.settings.starting());
    this.available.reset();
    this.active.reset();
  }

  CaseReportIdentifier create(final PatientIdentifier patient) {
    // Condition: Flu activity code (Influenza)
    return create(patient, "10570");
  }

  /**
   * Creates a Case Report associated with the given {@code patient} with the Out of System and STD
   * programJurisdictionOid
   */
  CaseReportIdentifier create(final PatientIdentifier patient, final String condition) {
    PatientIdentifier revision = patientMother.revise(patient);
    //  create a document
    NbsDocument document = new NbsDocument();
    long identifier = idGenerator.next();
    String local = idGenerator.nextLocal(DOCUMENT_CLASS);

    document.setId(identifier);
    document.setDocPayload("<?xml version=\"1.0\"?>");
    document.setDocTypeCd("PHC236");
    document.setLocalId(local);
    document.setNbsInterfaceUid(227L);
    document.setSharedInd('F');
    document.setVersionCtrlNbr((short) 1);
    document.setRecordStatusCd("ACTIVE");
    document.setRecordStatusTime(settings.createdOn().toInstant(ZoneOffset.UTC));

    document.setCd(condition);

    // Jurisdiction: Out of system
    document.setProgAreaCd("STD");
    document.setJurisdictionCd("999999");
    document.setProgramJurisdictionOid(1300200015L);   //  STD Out of System

    document.setAddTime(settings.createdOn().toInstant(ZoneOffset.UTC));
    document.setAddUserId(settings.createdBy());
    document.setLastChgTime(settings.createdOn().toInstant(ZoneOffset.UTC));
    document.setLastChgUserId(settings.createdBy());

    document.setNbsDocumentMetadataUid(metadatum());

    entityManager.persist(document);

    forPatient(revision.id(), identifier);

    CaseReportIdentifier created = new CaseReportIdentifier(identifier, local);
    include(created);
    return created;
  }

  private NbsDocumentMetadatum metadatum() {
    var ref = entityManager.find(NbsDocumentMetadatum.class, 1003L);
    if (ref == null) {
      var metadatum = new NbsDocumentMetadatum();
      metadatum.setId(1003L);
      metadatum.setXmlSchemaLocation("schemaLocation");
      metadatum.setDocumentViewXsl("docViewXsl");
      entityManager.persist(metadatum);
      return metadatum;
    }
    return ref;
  }

  private void forPatient(final long patient, final long document) {

    //  create the act
    Act act = new Act();
    act.setId(document);
    act.setClassCd(DOCUMENT_CLASS);
    act.setMoodCd("EVN");

    // create the participation
    ParticipationId identifier = new ParticipationId(patient, document, "SubjOfDoc");

    Participation participation = new Participation();
    participation.setId(identifier);
    participation.setActClassCd(DOCUMENT_CLASS);
    participation.setSubjectClassCd(PERSON_CLASS);

    participation.setRecordStatusCd(RecordStatus.ACTIVE);
    participation.setRecordStatusTime(settings.createdOn().toInstant(ZoneOffset.UTC));
    participation.setAddTime(settings.createdOn().toInstant(ZoneOffset.UTC));
    participation.setAddUserId(settings.createdBy());
    participation.setActUid(act);

    act.addParticipation(participation);

    entityManager.persist(act);

  }

  private void include(final CaseReportIdentifier identifier) {
    this.available.available(identifier);
    this.active.active(identifier);
  }

  void unprocessed(final CaseReportIdentifier identifier) {
    NbsDocument document = managed(identifier);
    document.setRecordStatusCd("UNPROCESSED");
  }

  void sentBy(final CaseReportIdentifier identifier, final String name) {
    NbsDocument document = managed(identifier);
    document.setSendingFacilityNm(name);
  }

  void receivedOn(final CaseReportIdentifier identifier, final Instant received) {
    NbsDocument document = managed(identifier);
    document.setAddTime(received);
  }

  void requiresSecurityAssignment(final CaseReportIdentifier identifier) {
    NbsDocument document = managed(identifier);
    document.setJurisdictionCd(null);
    document.setProgAreaCd(null);
    document.setProgramJurisdictionOid(null);
  }

  void updated(final CaseReportIdentifier identifier) {
    NbsDocument document = managed(identifier);

    short version = document.getExternalVersionCtrlNbr() == null
        ? 0
        : document.getExternalVersionCtrlNbr();

    document.setExternalVersionCtrlNbr(++version);
  }

  void withCondition(final CaseReportIdentifier identifier, final String condition) {
    NbsDocument document = managed(identifier);
    document.setCd(condition);
  }

  private NbsDocument managed(final CaseReportIdentifier identifier) {
    return this.entityManager.find(NbsDocument.class, identifier.identifier());
  }

}

package gov.cdc.nbs.patient.document;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Act;
import gov.cdc.nbs.entity.odse.NbsDocument;
import gov.cdc.nbs.entity.odse.NbsDocumentMetadatum;
import gov.cdc.nbs.entity.odse.Participation;
import gov.cdc.nbs.entity.odse.ParticipationId;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.identity.TestUniqueIdGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
class DocumentMother {

  private static final String DOCUMENT_CLASS = "DOC";
  private static final String PERSON_CLASS = "PSN";

  private final MotherSettings settings;
  private final TestUniqueIdGenerator idGenerator;
  private final EntityManager entityManager;
  private final TestDocumentCleaner cleaner;

  private final TestDocuments documents;

  DocumentMother(
      final MotherSettings settings,
      final TestUniqueIdGenerator idGenerator,
      final EntityManager entityManager,
      final TestDocumentCleaner cleaner,
      final TestDocuments documents
  ) {
    this.settings = settings;
    this.idGenerator = idGenerator;
    this.entityManager = entityManager;
    this.cleaner = cleaner;
    this.documents = documents;
  }

  void reset() {
    this.cleaner.clean(this.settings.starting());
    this.documents.reset();
  }

  /**
   * Creates a Case Report associated with the given {@code patient}
   *
   * @param patient The identifier of the patient.
   * @return A {@link NbsDocument} representing a Case Report associated with a patient.
   */
  NbsDocument caseReport(final long patient) {
    //  create a document
    NbsDocument document = new NbsDocument();
    long identifier = idGenerator.next();

    document.setId(identifier);
    document.setDocPayload("<?xml version=\"1.0\"?>");
    document.setDocTypeCd("PHC236");
    document.setLocalId(idGenerator.nextLocal(DOCUMENT_CLASS));

    document.setNbsInterfaceUid(227L);  // not sure what this refers to

    document.setSharedInd('F');
    document.setVersionCtrlNbr((short) 1);

    document.setRecordStatusCd("ACTIVE");
    document.setRecordStatusTime(settings.createdOn());
    document.setAddTime(settings.createdOn());
    document.setAddUserId(settings.createdBy());

    document.setNbsDocumentMetadataUid(metadatum());

    entityManager.persist(document);

    subjectOfDocument(patient, identifier);

    this.documents.available(document.getId());

    return document;
  }

  private Participation subjectOfDocument(final long patient, final long document) {

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
    participation.setRecordStatusTime(settings.createdOn());
    participation.setAddTime(settings.createdOn());
    participation.setAddUserId(settings.createdBy());
    participation.setActUid(act);

    act.addParticipation(participation);

    entityManager.persist(act);

    return participation;
  }

  private NbsDocumentMetadatum metadatum() {
    var ref = entityManager.find(NbsDocumentMetadatum.class,1003L);
  if (ref == null){
    var metadatum = new NbsDocumentMetadatum();
    metadatum.setId(1003L);
    metadatum.setXmlSchemaLocation("schemaLocation");
    metadatum.setDocumentViewXsl("docViewXsl");
    entityManager.persist(metadatum);
    return metadatum;
  } 
  return ref;
}
}

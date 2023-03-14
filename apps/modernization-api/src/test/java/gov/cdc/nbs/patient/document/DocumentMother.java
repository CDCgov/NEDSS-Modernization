package gov.cdc.nbs.patient.document;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.*;
import gov.cdc.nbs.identity.TestUniqueIdGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.time.Instant;

@Component
class DocumentMother {

  public static final String DOCUMENT_CLASS = "DOC";
  public static final String PERSON_CLASS = "PSN";
  private final TestUniqueIdGenerator idGenerator;

  private final EntityManager entityManager;
  private final JPAQueryFactory factory;

  DocumentMother(
      final TestUniqueIdGenerator idGenerator,
      final EntityManager entityManager,
      final JPAQueryFactory factory
  ) {
    this.idGenerator = idGenerator;
    this.entityManager = entityManager;
    this.factory = factory;
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

    Instant now = Instant.now();
    document.setRecordStatusCd("ACTIVE");
    document.setRecordStatusTime(now);
    document.setAddTime(now);
    document.setAddUserId(9999L);

    document.setNbsDocumentMetadataUid(metadatum());

    entityManager.persist(document);

    subjectOfDocument(patient, identifier);

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

    Instant now = Instant.now();
    participation.setRecordStatusCd(RecordStatus.ACTIVE);
    participation.setRecordStatusTime(now);
    participation.setAddTime(now);
    participation.setAddUserId(9999L);
    participation.setActUid(act);

    act.addParticipation(participation);

    entityManager.persist(act);

    return participation;
  }

  private NbsDocumentMetadatum metadatum() {
    return this.factory.select(QNbsDocumentMetadatum.nbsDocumentMetadatum)
        .from(QNbsDocumentMetadatum.nbsDocumentMetadatum)
        .where(QNbsDocumentMetadatum.nbsDocumentMetadatum.id.eq((long) 1003))
        .fetchOne();
  }
}

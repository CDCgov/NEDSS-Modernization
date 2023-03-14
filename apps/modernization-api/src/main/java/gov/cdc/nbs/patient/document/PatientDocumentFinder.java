package gov.cdc.nbs.patient.document;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.*;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Component
class PatientDocumentFinder {

  private static final QPublicHealthCase INVESTIGATION = new QPublicHealthCase("investigation");
  private static final QPerson PATIENT = QPerson.person;
  private static final QParticipation PARTICIPATION = QParticipation.participation;
  private static final QNbsDocument DOCUMENT = QNbsDocument.nbsDocument;
  private static final QCodeValueGeneral DOCUMENT_TYPE = QCodeValueGeneral.codeValueGeneral;
  private static final QActRelationship RELATIONSHIP = QActRelationship.actRelationship;
  private static final String DELETED = "LOG_DEL";
  private static final String SUBJECT_OF_DOCUMENT = "SubjOfDoc";
  private static final String DOCUMENT_TYPE_CODE_NAME_SET = "PUBLIC_HEALTH_EVENT";
  private static final String PERSON_CLASS = "PSN";
  private static final String DOCUMENT_CLASS = "DOC";
  private static final String INVESTIGATION_CLASS = "CASE";
  public static final String UPDATED_MODIFIER = "(Updated)";

  private final JPAQueryFactory factory;


  PatientDocumentFinder(final JPAQueryFactory factory) {
    this.factory = factory;
  }

  List<PatientDocument> find(final long patient) {
    return this.factory.selectDistinct(
            DOCUMENT.id,
            DOCUMENT.addTime,
            DOCUMENT_TYPE.codeShortDescTxt,
            DOCUMENT.sendingFacilityNm,
            DOCUMENT.cdDescTxt,
            DOCUMENT.localId,
            DOCUMENT.externalVersionCtrlNbr,
            INVESTIGATION.id,
            INVESTIGATION.localId
        ).from(PATIENT)
        .join(PARTICIPATION).on(
            PARTICIPATION.id.subjectEntityUid.eq(PATIENT.id),
            PARTICIPATION.id.typeCd.eq(SUBJECT_OF_DOCUMENT),
            PARTICIPATION.actClassCd.eq(DOCUMENT_CLASS),
            PARTICIPATION.subjectClassCd.eq(PERSON_CLASS),
            PARTICIPATION.recordStatusCd.eq(RecordStatus.ACTIVE)
        )
        .join(DOCUMENT).on(
            DOCUMENT.id.eq(PARTICIPATION.id.actUid),
            DOCUMENT.recordStatusCd.ne(DELETED)
        )
        .join(DOCUMENT_TYPE).on(
            DOCUMENT_TYPE.id.codeSetNm.eq(DOCUMENT_TYPE_CODE_NAME_SET),
            DOCUMENT_TYPE.id.code.eq(DOCUMENT.docTypeCd)
        )
        .leftJoin(RELATIONSHIP).on(
            RELATIONSHIP.targetClassCd.eq(INVESTIGATION_CLASS),
            RELATIONSHIP.sourceClassCd.eq(DOCUMENT_CLASS)
        )
        .leftJoin(INVESTIGATION).on(
            INVESTIGATION.id.eq(RELATIONSHIP.id.targetActUid),
            INVESTIGATION.recordStatusCd.ne(DELETED)
        )
        .where(PATIENT.personParentUid.id.eq(patient))
        .fetch()
        .stream()
        .map(this::map)
        .toList()
        ;
  }

  private PatientDocument map(final Tuple tuple) {
    Long identifier = Objects.requireNonNull(tuple.get(DOCUMENT.id), "A document id is required.");
    Instant added = tuple.get(DOCUMENT.addTime);
    String type = tuple.get(DOCUMENT_TYPE.codeShortDescTxt);
    String sendingFacility = tuple.get(DOCUMENT.sendingFacilityNm);
    String condition = tuple.get(DOCUMENT.cdDescTxt);
    String localId = tuple.get(DOCUMENT.localId);
    Short version = tuple.get(DOCUMENT.externalVersionCtrlNbr);

    String event = resolveEvent(localId, version);

    PatientDocument.Investigation investigation = maybeMapInvestigation(tuple);

    return new PatientDocument(
        identifier,
        added,
        type,
        sendingFacility,
        added,
        condition,
        event,
        investigation
    );
  }

  private String resolveEvent(final String localId, final Short version) {
    return version != null && version > 1
        ? localId + UPDATED_MODIFIER
        : localId;
  }

  private PatientDocument.Investigation maybeMapInvestigation(final Tuple tuple) {
    Long identifier = tuple.get(INVESTIGATION.id);
    String local = tuple.get(INVESTIGATION.localId);

    return identifier == null
        ? null
        : new PatientDocument.Investigation(
        identifier,
        local
    );
  }
}

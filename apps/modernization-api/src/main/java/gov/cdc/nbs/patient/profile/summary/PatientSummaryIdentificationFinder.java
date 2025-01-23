package gov.cdc.nbs.patient.profile.summary;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.odse.QEntityId;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;
import gov.cdc.nbs.patient.profile.summary.PatientSummary.PatientSummaryIdentification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class PatientSummaryIdentificationFinder {

  private static final String ACTIVE_CODE = "ACTIVE";
  private static final String IDENTIFICATION_TYPE_CODE_SET = "EI_TYPE_PAT";

  private static final QEntityId ENTITY_ID_TABLE = QEntityId.entityId;
  private static final QCodeValueGeneral CODE_VALUE_GENERAL_TABLE = QCodeValueGeneral.codeValueGeneral;

  private final JPAQueryFactory factory;

  PatientSummaryIdentificationFinder(final JPAQueryFactory factory) {
    this.factory = factory;
  }

  List<PatientSummaryIdentification> find(final long patient) {
    return resolve(patient);
  }

  private <R> JPAQuery<R> applyCriteria(final JPAQuery<R> query, final long patient) {
    return query.from(ENTITY_ID_TABLE)
        .where(
            ENTITY_ID_TABLE.id.entityUid.eq(patient),
            ENTITY_ID_TABLE.recordStatus.status.eq(ACTIVE_CODE),
            ENTITY_ID_TABLE.rootExtensionTxt.isNotNull()
        )
        .join(CODE_VALUE_GENERAL_TABLE).on(
            CODE_VALUE_GENERAL_TABLE.id.codeSetNm.eq(IDENTIFICATION_TYPE_CODE_SET),
            CODE_VALUE_GENERAL_TABLE.id.code.eq(ENTITY_ID_TABLE.typeCd))
        .orderBy(ENTITY_ID_TABLE.asOfDate.desc())
        .limit(2);
  }

  private List<PatientSummaryIdentification> resolve(final long patient) {
    return applyCriteria(
        this.factory.select(
            CODE_VALUE_GENERAL_TABLE.codeShortDescTxt,
            ENTITY_ID_TABLE.rootExtensionTxt),
        patient)
        .fetch()
        .stream()
        .map(this::map)
        .toList();
  }

  private PatientSummaryIdentification map(Tuple tuple) {
    return new PatientSummaryIdentification(tuple.get(CODE_VALUE_GENERAL_TABLE.codeShortDescTxt),
        tuple.get(ENTITY_ID_TABLE.rootExtensionTxt));
  }
}

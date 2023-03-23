package gov.cdc.nbs.patient.treatment;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.QActRelationship;
import gov.cdc.nbs.entity.odse.QParticipation;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.odse.QPersonName;
import gov.cdc.nbs.entity.odse.QPublicHealthCase;
import gov.cdc.nbs.entity.odse.QTreatment;
import gov.cdc.nbs.entity.odse.QTreatmentAdministered;
import gov.cdc.nbs.entity.srte.QConditionCode;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.patient.NameRenderer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Component
class PatientTreatmentFinder {

  private static final QPerson PATIENTS = QPerson.person;
  private static final String SUBJECT_OF_TREATMENT_CODE = "SubjOfTrmt";
  private static final String TREATMENT_CODE = "TRMT";
  private static final String PERSON_CODE = "PSN";
  private static final String TREATMENT_TO_PUBLIC_HEALTH_CASE_CODE = "TreatmentToPHC";
  private static final String CASE_CODE = "CASE";
  private static final String PROVIDER_OF_TREATMENT_CODE = "ProviderOfTrmt";
  private static final String ACTIVE = "ACTIVE";
  private static final String OPEN = "O";
  private static final String CLOSED = "C";
  private static final String DELETED = "LOG_DEL";
  private static final QTreatment TREATMENT = QTreatment.treatment;
  private static final QParticipation SUBJECT_OF_TREATMENT = new QParticipation("subject_of_treatment");
  private static final QTreatmentAdministered ADMINISTERED = QTreatmentAdministered.treatmentAdministered;
  private static final QActRelationship RELATIONSHIP = QActRelationship.actRelationship;
  private static final QParticipation TREATMENT_PROVIDER = new QParticipation("treatment_provider");
  private static final QPersonName PROVIDER = QPersonName.personName;
  private static final QPublicHealthCase INVESTIGATION = QPublicHealthCase.publicHealthCase;
  private static final QConditionCode CONDITION = QConditionCode.conditionCode;

  private final JPAQueryFactory factory;

  PatientTreatmentFinder(final JPAQueryFactory factory) {
    this.factory = factory;
  }

  List<PatientTreatment> find(long patient) {
    return resolveResults(patient);
  }

  private List<PatientTreatment> resolveResults(final long patient) {
    return applyCriteria(
        factory.selectDistinct(
            TREATMENT.id,
            RELATIONSHIP.addTime,
            TREATMENT.cdDescTxt,
            TREATMENT.localId,
            ADMINISTERED.effectiveFromTime,
            PROVIDER.nmPrefix,
            PROVIDER.firstNm,
            PROVIDER.lastNm,
            PROVIDER.nmSuffix,
            INVESTIGATION.id,
            INVESTIGATION.localId,
            CONDITION.conditionShortNm
        ),
        patient
    )
        .fetch()
        .stream()
        .map(this::mapTreatment)
        .toList();
  }

  private <R> JPAQuery<R> applyCriteria(final JPAQuery<R> query, final long patient) {
    return query.from(PATIENTS)
        .join(SUBJECT_OF_TREATMENT).on(
            SUBJECT_OF_TREATMENT.id.subjectEntityUid.eq(PATIENTS.id),
            SUBJECT_OF_TREATMENT.id.typeCd.eq(SUBJECT_OF_TREATMENT_CODE),
            SUBJECT_OF_TREATMENT.actClassCd.eq(TREATMENT_CODE),
            SUBJECT_OF_TREATMENT.subjectClassCd.eq(PERSON_CODE),
            SUBJECT_OF_TREATMENT.recordStatusCd.eq(RecordStatus.ACTIVE)
        )
        .join(TREATMENT).on(
            TREATMENT.id.eq(SUBJECT_OF_TREATMENT.actUid.id),
            TREATMENT.recordStatusCd.eq(ACTIVE)
        )
        .join(ADMINISTERED).on(
            ADMINISTERED.treatmentUid.id.eq(TREATMENT.id)
        )
        .join(RELATIONSHIP).on(
            RELATIONSHIP.id.typeCd.eq(TREATMENT_TO_PUBLIC_HEALTH_CASE_CODE),
            RELATIONSHIP.sourceActUid.id.eq(TREATMENT.id),
            RELATIONSHIP.sourceClassCd.eq(TREATMENT_CODE),
            RELATIONSHIP.targetClassCd.eq(CASE_CODE)
        )
        .leftJoin(TREATMENT_PROVIDER).on(
            TREATMENT_PROVIDER.actUid.id.eq(TREATMENT.id),
            TREATMENT_PROVIDER.id.typeCd.eq(PROVIDER_OF_TREATMENT_CODE),
            TREATMENT_PROVIDER.subjectClassCd.eq(PERSON_CODE)
        )
        .leftJoin(PROVIDER).on(
            PROVIDER.id.personUid.eq(TREATMENT_PROVIDER.id.subjectEntityUid)
        )
        .join(INVESTIGATION).on(
            INVESTIGATION.id.eq(RELATIONSHIP.targetActUid.id),
            INVESTIGATION.investigationStatusCd.in(OPEN, CLOSED),
            INVESTIGATION.recordStatusCd.ne(DELETED)
        )
        .join(CONDITION).on(
            CONDITION.id.eq(INVESTIGATION.cd)
        )
        .where(PATIENTS.personParentUid.id.eq(patient));
  }

  private PatientTreatment mapTreatment(final Tuple tuple) {
    Long treatment = Objects.requireNonNull(tuple.get(TREATMENT.id), "A treatment is required.");
    Instant createdOn = tuple.get(RELATIONSHIP.addTime);
    String description = tuple.get(TREATMENT.cdDescTxt);
    String event = tuple.get(TREATMENT.localId);
    Instant treatedOn = tuple.get(ADMINISTERED.effectiveFromTime);
    String providerPrefix = tuple.get(PROVIDER.nmPrefix);
    String providerFirstName = tuple.get(PROVIDER.firstNm);
    String providerLastName = tuple.get(PROVIDER.lastNm);
    Suffix providerSuffix = tuple.get(PROVIDER.nmSuffix);

    PatientTreatment.Investigation investigation = mapInvestigation(tuple);

    String provider = NameRenderer.render(
        providerPrefix,
        providerFirstName,
        providerLastName,
        providerSuffix
    );

    return new PatientTreatment(
        treatment,
        createdOn,
        provider,
        treatedOn,
        description,
        event,
        investigation
    );
  }

  private PatientTreatment.Investigation mapInvestigation(final Tuple tuple) {
    Long identifier = Objects.requireNonNull(tuple.get(INVESTIGATION.id),
        "An investigation is required.");
    String local = tuple.get(INVESTIGATION.localId);
    String condition = tuple.get(CONDITION.conditionShortNm);

    return new PatientTreatment.Investigation(
        identifier,
        local,
        condition
    );
  }

  Page<PatientTreatment> find(final long patient, final Pageable pageable) {
    long total = resolveTotal(patient);

    return total > 0
        ? new PageImpl<>(resolvePage(patient, pageable), pageable, total)
        : Page.empty(pageable);
  }

  private long resolveTotal(final long patient) {
    Long total = applyCriteria(factory.selectDistinct(TREATMENT.countDistinct()), patient)
        .fetchOne();
    return total == null ? 0L : total;
  }

  private List<PatientTreatment> resolvePage(final long patient, final Pageable pageable) {
    return applyCriteria(
        factory.selectDistinct(
            TREATMENT.id,
            RELATIONSHIP.addTime,
            TREATMENT.cdDescTxt,
            TREATMENT.localId,
            ADMINISTERED.effectiveFromTime,
            PROVIDER.nmPrefix,
            PROVIDER.firstNm,
            PROVIDER.lastNm,
            PROVIDER.nmSuffix,
            INVESTIGATION.id,
            INVESTIGATION.localId,
            CONDITION.conditionShortNm
        ),
        patient
    )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch()
        .stream()
        .map(this::mapTreatment)
        .toList();
  }
}

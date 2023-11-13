package gov.cdc.nbs.patient.profile.identification;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class PatientIdentificationFinder {

  private static final String PATIENT_CODE = "PAT";
  private static final String ACTIVE_CODE = "ACTIVE";
  private static final String IDENTIFICATION_TYPE_CODE_SET = "EI_TYPE_PAT";
  private static final String IDENTIFICATION_AUTHORITY_CODE_SET = "EI_AUTH_PAT";

  private final JPAQueryFactory factory;
  private final PatientIdentificationTupleMapper.Tables tables;
  private final PatientIdentificationTupleMapper mapper;

  PatientIdentificationFinder(final JPAQueryFactory factory) {
    this.factory = factory;
    this.tables = new PatientIdentificationTupleMapper.Tables();
    this.mapper = new PatientIdentificationTupleMapper(tables);
  }

  Page<PatientIdentification> find(final long patient, final Pageable pageable) {
    long total = resolveTotal(patient);

    return total > 0
        ? new PageImpl<>(resolvePage(patient, pageable), pageable, total)
        : Page.empty(pageable);
  }

  private long resolveTotal(final long patient) {
    Long total = applyCriteria(
        factory.selectDistinct(this.tables.patient().count()),
        patient
    )
        .fetchOne();
    return total == null ? 0L : total;
  }

  private <R> JPAQuery<R> applyCriteria(final JPAQuery<R> query, final long patient) {
    return query.from(this.tables.patient())
        .where(
            this.tables.patient().id.eq(patient),
            this.tables.patient().cd.eq(PATIENT_CODE)
        )
        .join(this.tables.identification()).on(
            this.tables.identification().id.entityUid.eq(this.tables.patient().id),
            this.tables.identification().recordStatusCd.eq(ACTIVE_CODE)
        )
        .join(this.tables.type()).on(
            this.tables.type().id.codeSetNm.eq(IDENTIFICATION_TYPE_CODE_SET),
            this.tables.type().id.code.eq(tables.identification().typeCd)
        )
        ;
  }

  private List<PatientIdentification> resolvePage(final long patient, final Pageable pageable) {
    return applyCriteria(
        this.factory.select(
            this.tables.patient().id,
            this.tables.patient().versionCtrlNbr,
            this.tables.identification().asOfDate,
            this.tables.identification().id.entityIdSeq,
            this.tables.identification().typeCd,
            this.tables.type().codeShortDescTxt,
            this.tables.identification().assigningAuthorityCd,
            this.tables.authority().codeShortDescTxt,
            this.tables.identification().rootExtensionTxt
        ),
        patient
    ).leftJoin(this.tables.authority()).on(
            this.tables.authority().id.codeSetNm.eq(IDENTIFICATION_AUTHORITY_CODE_SET),
            this.tables.authority().id.code.eq(tables.identification().assigningAuthorityCd)
        ).offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch()
        .stream()
        .map(mapper::map)
        .toList()
        ;
  }
}

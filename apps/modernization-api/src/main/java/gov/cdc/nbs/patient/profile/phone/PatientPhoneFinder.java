package gov.cdc.nbs.patient.profile.phone;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class PatientPhoneFinder {

  private static final String PATIENT_CODE = "PAT";
  private static final String ACTIVE_CODE = "ACTIVE";
  private static final String PHONE_TYPE_CODE_SET = "EL_TYPE_TELE_PAT";
  private static final String PHONE_USE_CODE_SET = "EL_USE_TELE_PAT";

  private final JPAQueryFactory factory;
  private final PatientPhoneTupleMapper.Tables tables;
  private final PatientPhoneTupleMapper mapper;

  PatientPhoneFinder(final JPAQueryFactory factory) {
    this.factory = factory;
    this.tables = new PatientPhoneTupleMapper.Tables();
    this.mapper = new PatientPhoneTupleMapper(tables);
  }

  private JPAQuery<Tuple> selection() {
    return this.factory.select(
        tables.patient().id,
        tables.locators().id.locatorUid,
        tables.locators().versionCtrlNbr,
        tables.locators().asOfDate,
        tables.locators().cd,
        tables.type().codeShortDescTxt,
        tables.locators().useCd,
        tables.use().codeShortDescTxt,
        tables.phoneNumber().cntryCd,
        tables.phoneNumber().phoneNbrTxt,
        tables.phoneNumber().extensionTxt,
        tables.phoneNumber().emailAddress,
        tables.phoneNumber().urlAddress,
        tables.locators().locatorDescTxt
    );
  }

  private <R> JPAQuery<R> applyCriteria(final JPAQuery<R> query, final long patient) {
    return query.from(this.tables.patient())
        .where(
            this.tables.patient().id.eq(patient),
            this.tables.patient().cd.eq(PATIENT_CODE)
        )
        .join(this.tables.locators()).on(
            this.tables.locators().id.entityUid.eq(this.tables.patient().id),
            this.tables.locators().recordStatusCd.eq(ACTIVE_CODE)
        )
        .join(this.tables.phoneNumber()).on(
            this.tables.phoneNumber().id.eq(this.tables.locators().id.locatorUid),
            this.tables.phoneNumber().recordStatus.recordStatusCd.eq(ACTIVE_CODE)
        )
        .join(this.tables.type()).on(
            this.tables.type().id.codeSetNm.eq(PHONE_TYPE_CODE_SET),
            this.tables.type().id.code.eq(this.tables.locators().cd)
        )
        .join(this.tables.use()).on(
            this.tables.use().id.codeSetNm.eq(PHONE_USE_CODE_SET),
            this.tables.use().id.code.eq(this.tables.locators().useCd)
        )
        ;
  }

  private long resolveTotal(final long patient) {
    Long total = applyCriteria(factory.selectDistinct(this.tables.phoneNumber().countDistinct()), patient)
        .fetchOne();
    return total == null ? 0L : total;
  }

  private List<PatientPhone> resolvePage(final long patient, final Pageable pageable) {
    return applyCriteria(
        selection(),
        patient
    )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch()
        .stream()
        .map(mapper::map)
        .toList()
        ;
  }

  Page<PatientPhone> find(final long patient, final Pageable pageable) {
    long total = resolveTotal(patient);

    return total > 0
        ? new PageImpl<>(resolvePage(patient, pageable), pageable, total)
        : Page.empty(pageable);
  }
}

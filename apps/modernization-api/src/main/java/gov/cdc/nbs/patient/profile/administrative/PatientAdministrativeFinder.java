package gov.cdc.nbs.patient.profile.administrative;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class PatientAdministrativeFinder {
  private static final String PATIENT_CODE = "PAT";

  private final JPAQueryFactory factory;
  private final PatientAdministrativeTupleMapper.Tables tables;
  private final PatientAdministrativeTupleMapper mapper;

  PatientAdministrativeFinder(final JPAQueryFactory factory) {
    this.factory = factory;
    this.tables = new PatientAdministrativeTupleMapper.Tables();
    mapper = new PatientAdministrativeTupleMapper(tables);
  }

  private <R> JPAQuery<R> applyCriteria(final JPAQuery<R> query, final long patient) {
    return query.from(this.tables.patient())
        .where(
            this.tables.patient().id.eq(patient),
            this.tables.patient().cd.eq(PATIENT_CODE),
            this.tables.patient().asOfDateAdmin.isNotNull()
        );
  }

  private long resolveTotal(final long patient) {
    Long total = applyCriteria(factory.selectDistinct(this.tables.patient().countDistinct()), patient)
        .fetchOne();
    return total == null ? 0L : total;
  }

  private List<PatientAdministrative> resolvePage(final long patient, final Pageable pageable) {
    return applyCriteria(
        this.factory.select(
            tables.patient().personParentUid.id,
            tables.patient().id,
            tables.patient().versionCtrlNbr,
            tables.patient().asOfDateAdmin,
            tables.patient().description
        ),
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

  Page<PatientAdministrative> find(final long patient, final Pageable pageable) {
    long total = resolveTotal(patient);

    return total > 0
        ? new PageImpl<>(resolvePage(patient, pageable), pageable, total)
        : Page.empty(pageable);
  }
}

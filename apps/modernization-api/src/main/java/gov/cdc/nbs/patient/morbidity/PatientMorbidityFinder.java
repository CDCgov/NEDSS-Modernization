package gov.cdc.nbs.patient.morbidity;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.enums.RecordStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class PatientMorbidityFinder {

    private static final String PATIENT_CLASS_CODE = "PSN";
    private static final String SUBJECT_OF_MORBIDITY_REPORT_TYPE_CODE = "SubjOfMorbReport";
    private static final String MORBIDITY_CLASS_CODE = "OBS";
    private static final String MORBIDITY_TYPE_CODE = "MorbReport";
    private static final String INVESTIGATION_CLASS_CODE = "CASE";
    private static final String LOG_DELETED = "LOG_DEL";
    private static final String OPEN = "O";
    private static final String CLOSED = "C";
    private static final String MORBIDITY_PROVIDER_TYPE_CODE = "PhysicianOfMorb";

    private final JPAQueryFactory factory;
    private final PatientMorbidityTables tables;
    private final PatientMorbidityTupleMapper mapper;

    PatientMorbidityFinder(final JPAQueryFactory factory) {
        this.factory = factory;
        this.tables = new PatientMorbidityTables();
        this.mapper = new PatientMorbidityTupleMapper(this.tables);
    }

    List<PatientMorbidity> find(final long patient) {
        return applyCriteria(
            selection(),
            patient
        ).fetch()
            .stream()
            .map(this.mapper::map)
            .collect(PatientMorbidityRowAccumulator.accumulating());
    }

    private JPAQuery<Tuple> selection() {
        return factory.selectDistinct(
            tables.morbidity().id,
            tables.morbidity().addTime,
            tables.provider().nmPrefix,
            tables.provider().firstNm,
            tables.provider().lastNm,
            tables.provider().nmSuffix,
            tables.morbidity().activityToTime,
            tables.condition().conditionShortNm,
            tables.jurisdiction().codeShortDescTxt,
            tables.investigation().id,
            tables.investigation().localId,
            tables.investigationCondition().conditionShortNm,
            tables.morbidity().localId
        );
    }

    private <R> JPAQuery<R> applyCriteria(final JPAQuery<R> query, final long patient) {
        return query.from(tables.morbidity())
            .join(tables.jurisdiction()).on(
                tables.jurisdiction().id.eq(tables.morbidity().jurisdictionCd)
            )
            .join(tables.subjectOfMorbidity()).on(
                tables.subjectOfMorbidity().id.typeCd.eq(SUBJECT_OF_MORBIDITY_REPORT_TYPE_CODE),
                tables.subjectOfMorbidity().id.actUid.eq(tables.morbidity().id),
                tables.subjectOfMorbidity().actClassCd.eq(MORBIDITY_CLASS_CODE),
                tables.subjectOfMorbidity().subjectClassCd.eq(PATIENT_CLASS_CODE),
                tables.subjectOfMorbidity().recordStatusCd.eq(RecordStatus.ACTIVE)
            )
            .join(tables.condition()).on(
                tables.condition().id.eq(tables.morbidity().cd)
            )
            .join(tables.subject()).on(
                tables.subject().id.eq(tables.subjectOfMorbidity().id.subjectEntityUid)
            )
            .leftJoin(tables.caseOfMorbidity()).on(
                tables.caseOfMorbidity().id.typeCd.eq(MORBIDITY_TYPE_CODE),
                tables.caseOfMorbidity().id.sourceActUid.eq(tables.morbidity().id),
                tables.caseOfMorbidity().sourceClassCd.eq(MORBIDITY_CLASS_CODE),
                tables.caseOfMorbidity().targetClassCd.eq(INVESTIGATION_CLASS_CODE)
            )
            .leftJoin(tables.investigation()).on(
                tables.investigation().id.eq(tables.caseOfMorbidity().id.targetActUid),
                tables.investigation().investigationStatusCd.in(OPEN, CLOSED),
                tables.investigation().recordStatusCd.ne(LOG_DELETED)
            )
            .leftJoin(tables.morbidityProvider()).on(
                tables.morbidityProvider().id.actUid.eq(tables.morbidity().id),
                tables.morbidityProvider().id.typeCd.eq(MORBIDITY_PROVIDER_TYPE_CODE),
                tables.morbidityProvider().subjectClassCd.eq(PATIENT_CLASS_CODE)
            )
            .leftJoin(tables.provider()).on(
                tables.provider().id.personUid.eq(tables.morbidityProvider().id.subjectEntityUid)
            )
            .leftJoin(tables.investigationCondition()).on(
                tables.investigationCondition().id.eq(tables.investigation().cd)
            )
            .where(tables.subject().personParentUid.id.eq(patient));
    }

    Page<PatientMorbidity> find(final long patient, final Pageable pageable) {
        long total = resolveTotal(patient);

        return total > 0
            ? new PageImpl<>(resolvePage(patient, pageable), pageable, total)
            : Page.empty(pageable);
    }

    private long resolveTotal(final long patient) {
        Long total = applyCriteria(factory.selectDistinct(tables.morbidity().countDistinct()), patient)
            .fetchOne();
        return total == null ? 0L : total;
    }

    private List<PatientMorbidity> resolvePage(final long patient, final Pageable pageable) {
        return applyCriteria(
            selection(),
            patient
        )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch()
            .stream()
            .map(this.mapper::map)
            .collect(PatientMorbidityRowAccumulator.accumulating())
            ;
    }

}

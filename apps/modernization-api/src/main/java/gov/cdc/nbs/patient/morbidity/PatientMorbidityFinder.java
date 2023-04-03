package gov.cdc.nbs.patient.morbidity;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
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
    private static final String TREATMENT_TO_MORBIDITY_RELATION = "TreatmentToMorb";
    private static final String TREATMENT_CLASS_CODE = "TRMT";
    private static final String ACTIVE_RECORD = "ACTIVE";
    private static final String COMPONENT_RELATION_CODE = "COMP";
    private static final String RESULT_DOMAIN_CODE = "Result";
    private static final String LAB_REPORT_RELATION_CODE = "LabReport";

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
            tables.morbidity().localId,
            tables.treatment().cdDescTxt,
            tables.labOrderResults().labTest().labTestDescTxt,
            tables.labOrderResults().status().codeShortDescTxt,
            tables.labOrderResults().codedLabResult().labResultDescTxt,
            tables.labOrderResults().numericResult().comparatorCd1,
            tables.labOrderResults().numericResult().numericValue1,
            tables.labOrderResults().textResult().valueTxt
        );
    }

    private <R> JPAQuery<R> applyBaseCriteria(final JPAQuery<R> query, final long patient) {
        return query.from(tables.morbidity())
            .join(tables.jurisdiction()).on(
                tables.jurisdiction().id.eq(tables.morbidity().jurisdictionCd)
            )
            .join(tables.condition()).on(
                tables.condition().id.eq(tables.morbidity().cd)
            )
            //  subject
            .join(tables.subjectOfMorbidity()).on(
                tables.subjectOfMorbidity().id.typeCd.eq(SUBJECT_OF_MORBIDITY_REPORT_TYPE_CODE),
                tables.subjectOfMorbidity().id.actUid.eq(tables.morbidity().id),
                tables.subjectOfMorbidity().actClassCd.eq(MORBIDITY_CLASS_CODE),
                tables.subjectOfMorbidity().subjectClassCd.eq(PATIENT_CLASS_CODE),
                tables.subjectOfMorbidity().recordStatusCd.eq(RecordStatus.ACTIVE)
            )
            .join(tables.subject()).on(
                tables.subject().id.eq(tables.subjectOfMorbidity().id.subjectEntityUid)
            ).where(tables.subject().personParentUid.id.eq(patient));
    }

    private <R> JPAQuery<R> applyCriteria(final JPAQuery<R> query, final long patient) {
        return applyBaseCriteria(query, patient)
            //  provider
            .leftJoin(tables.morbidityProvider()).on(
                tables.morbidityProvider().id.actUid.eq(tables.morbidity().id),
                tables.morbidityProvider().id.typeCd.eq(MORBIDITY_PROVIDER_TYPE_CODE),
                tables.morbidityProvider().subjectClassCd.eq(PATIENT_CLASS_CODE)
            )
            .leftJoin(tables.provider()).on(
                tables.provider().id.personUid.eq(tables.morbidityProvider().id.subjectEntityUid)
            )
            //  investigation
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
            .leftJoin(tables.investigationCondition()).on(
                tables.investigationCondition().id.eq(tables.investigation().cd)
            )
            //  treatment is linked to a morbidity report through a TreatmentToMorb relationship where the source is the
            //  treatment and the target is the morbidity report.
            .leftJoin(tables.treatmentOfMorbidity()).on(
                tables.treatmentOfMorbidity().id.targetActUid.eq(tables.morbidity().id),
                tables.treatmentOfMorbidity().id.typeCd.eq(TREATMENT_TO_MORBIDITY_RELATION),
                tables.treatmentOfMorbidity().targetClassCd.eq(MORBIDITY_CLASS_CODE),
                tables.treatmentOfMorbidity().sourceClassCd.eq(TREATMENT_CLASS_CODE)
            )
            .leftJoin(tables.treatment()).on(
                tables.treatment().id.eq(tables.treatmentOfMorbidity().sourceActUid.id),
                tables.treatment().recordStatusCd.eq(ACTIVE_RECORD)
            )
            // A lab report is linked to a morbidity report through a LabReport relationship where the source is the
            // observation defining the ordering of the Lab Test and the and the target is the morbidity report
            .leftJoin(tables.morbidityLabReport()).on(
                tables.morbidityLabReport().id.targetActUid.eq(tables.morbidity().id),
                tables.morbidityLabReport().id.typeCd.eq(LAB_REPORT_RELATION_CODE),
                tables.morbidityLabReport().sourceClassCd.eq(MORBIDITY_CLASS_CODE),
                tables.morbidityLabReport().targetClassCd.eq(MORBIDITY_CLASS_CODE)
            )
            .leftJoin(tables.order()).on(
                tables.order().id.eq(tables.morbidityLabReport().sourceActUid.id)
            )
            .leftJoin(tables.labOrderResults().status()).on(
                tables.labOrderResults().status().id.codeSetNm.eq(Expressions.constant("ACT_OBJ_ST")),
                //  codes are identified by varchar(20) however, status_cd is a char(1) on observation
                tables.labOrderResults().status().id.code.charAt(0).eq(tables.order().statusCd)
            )
            //      The Results of a lab report are linked to the lab order through a COMP relationship where the source
            // is the observation lab order and the target is the observation lab results
            .leftJoin(tables.labReportComponents()).on(
                tables.labReportComponents().id.typeCd.eq(COMPONENT_RELATION_CODE),
                tables.labReportComponents().id.targetActUid.eq(tables.order().id),
                tables.labReportComponents().targetClassCd.eq(MORBIDITY_CLASS_CODE),
                tables.labReportComponents().sourceClassCd.eq(MORBIDITY_CLASS_CODE)
            )
            .leftJoin(tables.labResult()).on(
                tables.labResult().id.eq(tables.labReportComponents().id.sourceActUid),
                tables.labResult().obsDomainCdSt1.eq(RESULT_DOMAIN_CODE)
            )
            //  the test that was ordered
            .leftJoin(tables.labOrderResults().labTest()).on(
                tables.labOrderResults().labTest().id.labTestCd.eq(tables.labResult().cd)
            )
            //  The lab result values
            //      Coded value
            .leftJoin(tables.labOrderResults().codedResult()).on(
                tables.labOrderResults().codedResult().id.observationUid.eq(tables.labResult().id)
            )
            .leftJoin(tables.labOrderResults().codedLabResult()).on(
                tables.labOrderResults().codedLabResult().id.labResultCd.eq(tables.labOrderResults().codedResult().id.code)
            )
            //      Numeric value
            .leftJoin(tables.labOrderResults().numericResult()).on(
                tables.labOrderResults().numericResult().id.observationUid.eq(tables.labResult().id)
            )
            //      Text value
            .leftJoin(tables.labOrderResults().textResult()).on(
                tables.labOrderResults().textResult().id.observationUid.eq(tables.labResult().id)
            )
            ;
    }

    Page<PatientMorbidity> find(final long patient, final Pageable pageable) {
        long total = resolveTotal(patient);

        return total > 0
            ? new PageImpl<>(resolvePage(patient, pageable), pageable, total)
            : Page.empty(pageable);
    }

    private long resolveTotal(final long patient) {
        Long total = applyBaseCriteria(factory.selectDistinct(tables.morbidity().countDistinct()), patient)
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

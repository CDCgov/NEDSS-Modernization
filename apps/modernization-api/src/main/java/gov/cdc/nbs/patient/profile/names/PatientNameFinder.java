package gov.cdc.nbs.patient.profile.names;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class PatientNameFinder {

        private static final String ACTIVE_CODE = "ACTIVE";
        private static final String NAME_USE_CODE_SET = "P_NM_USE";
        private static final String NAME_PREFIX_CODE_SET = "P_NM_PFX";
        private static final String NAME_DEGREE_CODE_SET = "P_NM_DEG";
        private static final String PATIENT_CODE = "PAT";

        private final JPAQueryFactory factory;
        private final PatientNameTupleMapper.Tables tables;
        private final PatientNameTupleMapper mapper;

        PatientNameFinder(final JPAQueryFactory factory) {
                this.factory = factory;
                this.tables = new PatientNameTupleMapper.Tables();
                mapper = new PatientNameTupleMapper(tables);
        }

        private <R> JPAQuery<R> applyCriteria(final JPAQuery<R> query, final long patient) {
                return query.from(this.tables.patient())
                                .where(
                                                this.tables.patient().id.eq(patient),
                                                this.tables.patient().cd.eq(PATIENT_CODE))
                                .join(this.tables.name()).on(
                                                this.tables.name().id.personUid.eq(this.tables.patient().id),
                                                this.tables.name().recordStatus.status.eq(ACTIVE_CODE))
                                .join(this.tables.use()).on(
                                                this.tables.use().id.codeSetNm.eq(NAME_USE_CODE_SET),
                                                this.tables.use().id.code.eq(tables.name().nmUseCd));
        }

        private long resolveTotal(final long patient) {
                Long total = applyCriteria(this.factory.select(this.tables.name().id.personNameSeq.countDistinct()),
                                patient)
                                                .fetchOne();
                return total == null ? 0L : total;
        }

        private List<PatientName> resolvePage(final long patient, final Pageable pageable) {
                return applyCriteria(
                                this.factory.select(
                                                tables.name().id.personUid,
                                                tables.patient().versionCtrlNbr,
                                                tables.name().asOfDate,
                                                tables.name().id.personNameSeq,
                                                tables.name().nmUseCd,
                                                tables.use().codeShortDescTxt,
                                                tables.name().nmPrefix,
                                                tables.prefix().codeShortDescTxt,
                                                tables.name().firstNm,
                                                tables.name().middleNm,
                                                tables.name().middleNm2,
                                                tables.name().lastNm,
                                                tables.name().lastNm2,
                                                tables.name().nmSuffix,
                                                tables.name().nmDegree,
                                                tables.degree().codeDescTxt),
                                patient)
                                                .leftJoin(this.tables.prefix()).on(
                                                                this.tables.prefix().id.codeSetNm
                                                                                .eq(NAME_PREFIX_CODE_SET),
                                                                this.tables.prefix().id.code
                                                                                .eq(this.tables.name().nmPrefix))
                                                .leftJoin(this.tables.degree()).on(
                                                                this.tables.degree().id.codeSetNm
                                                                                .eq(NAME_DEGREE_CODE_SET),
                                                                this.tables.degree().id.code
                                                                                .eq(this.tables.name().nmDegree))
                                                .offset(pageable.getOffset())
                                                .limit(pageable.getPageSize())
                                                .fetch()
                                                .stream()
                                                .map(mapper::map)
                                                .toList();
        }

        Page<PatientName> find(final long patient, final Pageable pageable) {
                long total = resolveTotal(patient);

                return total > 0
                                ? new PageImpl<>(resolvePage(patient, pageable), pageable, total)
                                : Page.empty(pageable);
        }

}

package gov.cdc.nbs.patient.profile.race;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.accumulation.Accumulator;
import gov.cdc.nbs.entity.enums.RecordStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class PatientRaceFinder {

    private static final String PATIENT_CODE = "PAT";
    private static final String ACTIVE_CODE = "ACTIVE";

    private final JPAQueryFactory factory;
    private final PatientRaceTupleMapper.Tables tables;
    private final PatientRaceTupleMapper mapper;
    private final PatientRaceMerger merger;

    PatientRaceFinder(final JPAQueryFactory factory) {
        this.factory = factory;
        this.tables = new PatientRaceTupleMapper.Tables();
        this.mapper = new PatientRaceTupleMapper(tables);
        this.merger = new PatientRaceMerger();
    }

    Page<PatientRace> find(final long patient, final Pageable pageable) {
        long total = resolveTotal(patient);

        return total > 0
            ? new PageImpl<>(resolvePage(patient, pageable), pageable, total)
            : Page.empty(pageable);
    }

    private long resolveTotal(final long patient) {
        Long total = applyCriteria(
            factory.selectDistinct(this.tables.patient().countDistinct()),
            patient
        )
            .fetchOne();
        return total == null ? 0L : total;
    }

    private <R> JPAQuery<R> applyCriteria(final JPAQuery<R> query, final long patient) {
        return query.from(this.tables.patient())
            .join(this.tables.personRace()).on(
                this.tables.personRace().personUid.id.eq(this.tables.patient().id),
                this.tables.personRace().recordStatusCd.eq(ACTIVE_CODE)
            )
            .join(this.tables.category()).on(
                this.tables.category().id.eq(this.tables.personRace().raceCategoryCd)
            )
            .join(this.tables.race()).on(
                this.tables.race().id.eq(this.tables.personRace().raceCd)
            )
            .where(
                this.tables.patient().id.eq(patient),
                this.tables.patient().cd.eq(PATIENT_CODE),
                this.tables.patient().recordStatusCd.eq(RecordStatus.ACTIVE)
            );
    }

    private List<PatientRace> resolvePage(final long patient, final Pageable pageable) {
        return applyCriteria(
            this.factory.select(
                this.tables.patient().personParentUid.id,
                this.tables.patient().id,
                this.tables.patient().versionCtrlNbr,
                this.tables.personRace().asOfDate,
                this.tables.category().id,
                this.tables.category().codeShortDescTxt,
                this.tables.race().id,
                this.tables.race().codeShortDescTxt
            ),
            patient
        )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch()
            .stream()
            .map(mapper::map)
            .collect(Accumulator.collecting(PatientRace::patient, this.merger::merge))
            ;
    }
}

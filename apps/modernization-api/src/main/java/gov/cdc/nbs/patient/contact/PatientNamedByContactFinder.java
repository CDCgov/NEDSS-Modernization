package gov.cdc.nbs.patient.contact;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class PatientNamedByContactFinder {

    private final JPAQueryFactory factory;
    private final PatientNamedByContactTupleMapper.Tables tables;
    private final PatientNamedByContactTupleMapper mapper;

    PatientNamedByContactFinder(final JPAQueryFactory factory) {
        this.factory = factory;
        this.tables = new PatientNamedByContactTupleMapper.Tables();
        this.mapper = new PatientNamedByContactTupleMapper(this.tables);
    }

    Page<PatientContacts.NamedByContact> find(final long patient, final Pageable pageable) {
        long total = resolveTotal(patient);

        return total > 0
            ? new PageImpl<>(resolvePage(patient, pageable), pageable, total)
            : Page.empty(pageable);
    }

    private <R> JPAQuery<R> applyCriteria(final JPAQuery<R> query, final long patient) {
        return query.from(this.tables.tracing())
            .join(this.tables.associatedWith().investigation()).on(
                this.tables.associatedWith().investigation().id.eq(this.tables.tracing().subjectEntityPhcUid.id)
            )
            .join(this.tables.associatedWith().condition()).on(
                this.tables.associatedWith().condition().id.eq(this.tables.associatedWith().investigation().cd)
            )
            .join(this.tables.contact()).on(
                this.tables.contact().id.eq(this.tables.tracing().contactNBSEntityUid.id)
            )
            .join(this.tables.subject()).on(
                this.tables.tracing().subjectNBSEntityUid.id.eq(this.tables.subject().id)
            )
            .leftJoin(this.tables.interview()).on(
                this.tables.interview().id.eq(this.tables.tracing().namedDuringInterviewUid)
            )
            .where(this.tables.tracing().recordStatusCd.eq("ACTIVE"),
                this.tables.contact().personParentUid.id.eq(patient));
    }


    private long resolveTotal(final long patient) {
        Long total = applyCriteria(factory.selectDistinct(this.tables.tracing().countDistinct()), patient)
            .fetchOne();
        return total == null ? 0L : total;
    }

    private List<PatientContacts.NamedByContact> resolvePage(
        final long patient,
        final Pageable pageable
    ) {
        return applyCriteria(
            factory.selectDistinct(
                this.tables.tracing().id,
                this.tables.tracing().addTime,
                this.tables.tracing().localId,
                this.tables.subject().localId,
                this.tables.subject().nmPrefix,
                this.tables.subject().firstNm,
                this.tables.subject().lastNm,
                this.tables.subject().nmSuffix,
                this.tables.associatedWith().investigation().id,
                this.tables.associatedWith().investigation().localId,
                this.tables.associatedWith().condition().id,
                this.tables.associatedWith().condition().conditionShortNm,
                this.tables.namedOn()
            ),
            patient
        )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch()
            .stream()
            .map(this.mapper::map)
            .toList();
    }
}

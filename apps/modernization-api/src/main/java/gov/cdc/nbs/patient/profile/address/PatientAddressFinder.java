package gov.cdc.nbs.patient.profile.address;

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
class PatientAddressFinder {
    private static final String PATIENT_CODE = "PAT";
    private static final String ACTIVE_CODE = "ACTIVE";
    private static final String POSTAL_TYPE_CODE_SET = "EL_TYPE_PST_PAT";
    private static final String POSTAL_USE_CODE_SET = "EL_USE_PST_PAT";
    private static final String COUNTY_CODE_SET = "PHVS_COUNTY_FIPS_6-4";

    private final JPAQueryFactory factory;
    private final PatientAddressTables tables;
    private final PatientAddressTupleMapper mapper;

    PatientAddressFinder(final JPAQueryFactory factory) {
        this.factory = factory;
        this.tables = new PatientAddressTables();
        this.mapper = new PatientAddressTupleMapper(tables);
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
            tables.address().streetAddr1,
            tables.address().streetAddr2,
            tables.address().cityDescTxt,
            tables.address().cntyCd,
            tables.county().codeShortDescTxt,
            tables.address().stateCd,
            tables.state().codeDescTxt,
            tables.address().zipCd,
            tables.address().cntryCd,
            tables.country().codeDescTxt,
            tables.address().censusTract,
            tables.locators().locatorDescTxt
        );
    }

    private <R> JPAQuery<R> applyBaseCriteria(final JPAQuery<R> query, final long patient) {
        return query.from(this.tables.patient())
            .where(
                this.tables.patient().personParentUid.id.eq(patient),
                this.tables.patient().cd.eq(PATIENT_CODE),
                this.tables.patient().recordStatusCd.eq(RecordStatus.ACTIVE)
            )
            .join(this.tables.locators()).on(
                this.tables.locators().id.entityUid.eq(this.tables.patient().id),
                this.tables.locators().recordStatusCd.eq(ACTIVE_CODE)
            )
            .join(this.tables.address()).on(
                this.tables.address().id.eq(this.tables.locators().id.locatorUid),
                this.tables.address().recordStatusCd.eq(ACTIVE_CODE)
            )
            .join(this.tables.type()).on(
                this.tables.type().id.codeSetNm.eq(POSTAL_TYPE_CODE_SET),
                this.tables.type().id.code.eq(this.tables.locators().cd)
            )
            .join(this.tables.use()).on(
                this.tables.use().id.codeSetNm.eq(POSTAL_USE_CODE_SET),
                this.tables.use().id.code.eq(this.tables.locators().useCd)
            )
            ;
    }

    private <R> JPAQuery<R> applyCriteria(final JPAQuery<R> query, final long patient) {
        return applyBaseCriteria(query, patient)
            .leftJoin(this.tables.county()).on(
                this.tables.county().id.codeSetNm.eq(COUNTY_CODE_SET),
                this.tables.county().id.code.eq(this.tables.address().cntyCd)
            )
            .leftJoin(this.tables.state()).on(
                this.tables.state().id.eq(this.tables.address().stateCd)
            )
            .leftJoin(this.tables.country()).on(
                this.tables.country().id.eq(this.tables.address().cntryCd)
            )
            ;
    }

    private long resolveTotal(final long patient) {
        Long total = applyBaseCriteria(factory.selectDistinct(this.tables.address().countDistinct()), patient)
            .fetchOne();
        return total == null ? 0L : total;
    }

    private List<PatientAddress> resolvePage(final long patient, final Pageable pageable) {
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

    Page<PatientAddress> find(final long patient, final Pageable pageable) {
        long total = resolveTotal(patient);

        return total > 0
            ? new PageImpl<>(resolvePage(patient, pageable), pageable, total)
            : Page.empty(pageable);
    }

}

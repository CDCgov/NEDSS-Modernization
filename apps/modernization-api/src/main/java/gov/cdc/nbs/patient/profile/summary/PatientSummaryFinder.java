package gov.cdc.nbs.patient.profile.summary;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.odse.QPersonName;
import gov.cdc.nbs.entity.odse.QPersonRace;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
class PatientSummaryFinder {


    private static final QPersonName EFF_NAME = new QPersonName("eff_name");
    private static final QPersonName SEQ_NAME = new QPersonName("seq_name");
    private static final QPersonRace PERSON_RACE = new QPersonRace("person_race");
    private static final QPersonRace EFF_RACE = new QPersonRace("eff_race");
    private static final String LEGAL_NAME_CODE = "L";
    private static final String ACTIVE_CODE = "ACTIVE";
    private static final String NAME_PREFIX_CODE_SET = "P_NM_PFX";
    private static final String ETHNIC_GROUP_CODE_SET = "P_ETHN_GRP";
    private static final String PATIENT_CODE = "PAT";
    private final JPAQueryFactory factory;
    private final PatientSummaryTupleMapper mapper;
    private final PatientSummaryTables tables;

    PatientSummaryFinder(final JPAQueryFactory factory) {
        this.factory = factory;
        this.tables = new PatientSummaryTables();
        this.mapper = new PatientSummaryTupleMapper(tables);
    }

    Optional<PatientSummary> find(final long identifier, final Instant asOf) {
        return this.factory.selectDistinct(
                this.tables.patient().localId,
                this.tables.prefix().codeShortDescTxt,
                this.tables.name().firstNm,
                this.tables.name().middleNm,
                this.tables.name().lastNm,
                this.tables.name().nmSuffix,
                this.tables.patient().currSexCd,
                this.tables.patient().birthTime,
                this.tables.ethnicity().codeShortDescTxt,
                this.tables.race().codeShortDescTxt
            ).from(this.tables.patient())
            //  Most effective Legal name for the as of
            .leftJoin(this.tables.name()).on(
                this.tables.name().id.personUid.eq(this.tables.patient().id),
                this.tables.name().nmUseCd.eq(LEGAL_NAME_CODE),
                this.tables.name().recordStatusCd.eq(ACTIVE_CODE),
                this.tables.name().asOfDate.eq(
                    JPAExpressions.select(EFF_NAME.asOfDate.max())
                        .from(EFF_NAME)
                        .where(
                            EFF_NAME.id.personUid.eq(this.tables.name().id.personUid),
                            EFF_NAME.recordStatusCd.eq(this.tables.name().recordStatusCd),
                            EFF_NAME.nmUseCd.eq(this.tables.name().nmUseCd),
                            EFF_NAME.asOfDate.loe(asOf)
                        )
                ),
                this.tables.name().id.personNameSeq.eq(
                    JPAExpressions.select(SEQ_NAME.id.personNameSeq.max())
                        .from(SEQ_NAME)
                        .where(
                            SEQ_NAME.id.personUid.eq(this.tables.name().id.personUid),
                            SEQ_NAME.recordStatusCd.eq(this.tables.name().recordStatusCd),
                            SEQ_NAME.nmUseCd.eq(this.tables.name().nmUseCd),
                            SEQ_NAME.asOfDate.eq(this.tables.name().asOfDate)
                        )
                )
            )
            .leftJoin(this.tables.prefix()).on(
                this.tables.prefix().id.codeSetNm.eq(NAME_PREFIX_CODE_SET),
                this.tables.prefix().id.code.eq(this.tables.name().nmPrefix)
            )
            //  Most effective Race for the as of
            .leftJoin(PERSON_RACE).on(
                PERSON_RACE.personUid.eq(this.tables.patient()),
                PERSON_RACE.recordStatusCd.eq(ACTIVE_CODE),
                PERSON_RACE.asOfDate.eq(
                    JPAExpressions.select(EFF_RACE.asOfDate.max())
                        .from(EFF_RACE)
                        .where(
                            EFF_RACE.personUid.eq(PERSON_RACE.personUid),
                            EFF_RACE.asOfDate.loe(asOf),
                            EFF_RACE.recordStatusCd.eq(PERSON_RACE.recordStatusCd)
                        )
                )
            )
            .leftJoin(this.tables.race()).on(
                this.tables.race().id.eq(PERSON_RACE.raceCategoryCd)
            )
            //
            .leftJoin(this.tables.ethnicity()).on(
                this.tables.ethnicity().id.codeSetNm.eq(ETHNIC_GROUP_CODE_SET),
                this.tables.ethnicity().id.code.eq(this.tables.patient().ethnicGroupInd)
            )
            .where(this.tables.patient().personParentUid.id.eq(identifier), this.tables.patient().cd.eq(PATIENT_CODE))
            .fetch()
            .stream()
            .map(mapper::map)
            .findFirst()
            ;
    }
}

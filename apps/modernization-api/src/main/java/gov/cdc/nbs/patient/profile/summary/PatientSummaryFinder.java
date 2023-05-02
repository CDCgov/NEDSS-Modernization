package gov.cdc.nbs.patient.profile.summary;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.accumulation.Accumulator;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.odse.QPersonName;
import gov.cdc.nbs.entity.odse.QPersonRace;
import gov.cdc.nbs.entity.odse.QPostalEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.QTeleEntityLocatorParticipation;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
class PatientSummaryFinder {

    private static final QPersonName EFF_NAME = new QPersonName("eff_name");
    private static final QPersonName SEQ_NAME = new QPersonName("seq_name");
    private static final QPersonRace PERSON_RACE = new QPersonRace("person_race");
    private static final QPersonRace EFF_RACE = new QPersonRace("eff_race");

    private static final QPostalEntityLocatorParticipation EFF_HOME = new QPostalEntityLocatorParticipation("eff_home");
    private static final QTeleEntityLocatorParticipation EFF_PHONE = new QTeleEntityLocatorParticipation("eff_phone");

    private static final QPerson EFF_PHONE_CHILD = new QPerson("eff_phone_child");
    private static final QTeleEntityLocatorParticipation EFF_EMAIL = new QTeleEntityLocatorParticipation("eff_email");

    private static final QPerson EFF_EMAIL_CHILD = new QPerson("eff_email_child");

    private static final String LEGAL_NAME_CODE = "L";
    private static final String ACTIVE_CODE = "ACTIVE";
    private static final String NAME_PREFIX_CODE_SET = "P_NM_PFX";
    private static final String ETHNIC_GROUP_CODE_SET = "P_ETHN_GRP";
    private static final String PATIENT_CODE = "PAT";
    private static final String HOME_ADDRESS_CODE = "H";
    private static final String EMAIL_CODE = "NET";

    private final JPAQueryFactory factory;
    private final PatientSummaryTupleMapper.Tables tables;
    private final PatientSummaryTupleMapper mapper;
    private final PatientSummaryMerger merger;

    PatientSummaryFinder(final JPAQueryFactory factory) {
        this.factory = factory;
        this.tables = new PatientSummaryTupleMapper.Tables();
        this.mapper = new PatientSummaryTupleMapper(tables);
        this.merger = new PatientSummaryMerger();
    }

    Optional<PatientSummary> find(final long identifier, final Instant asOf) {
        return this.factory.selectDistinct(
                this.tables.patient().personParentUid.id,
                this.tables.prefix().codeShortDescTxt,
                this.tables.name().firstNm,
                this.tables.name().middleNm,
                this.tables.name().lastNm,
                this.tables.name().nmSuffix,
                this.tables.patient().currSexCd,
                this.tables.patient().birthTime,
                this.tables.ethnicity().codeShortDescTxt,
                this.tables.race().codeShortDescTxt,
                this.tables.phoneNumber().phoneNbrTxt,
                this.tables.phoneUse().codeShortDescTxt,
                this.tables.email().emailAddress,
                this.tables.emailUse().codeShortDescTxt,
                this.tables.address().streetAddr1,
                this.tables.address().cityDescTxt,
                this.tables.state().stateNm,
                this.tables.address().zipCd,
                this.tables.country().codeDescTxt
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
            //  Most effective Home Address for the as of
            .leftJoin(this.tables.home()).on(
                this.tables.home().id.entityUid.eq(this.tables.patient().id),
                this.tables.home().recordStatusCd.eq(ACTIVE_CODE),
                this.tables.home().useCd.eq(HOME_ADDRESS_CODE),
                this.tables.home().asOfDate.eq(
                    JPAExpressions.select(EFF_HOME.asOfDate.max())
                        .from(EFF_HOME)
                        .where(
                            EFF_HOME.id.entityUid.eq(this.tables.patient().id),
                            EFF_HOME.useCd.eq(this.tables.home().useCd),
                            EFF_HOME.asOfDate.loe(asOf)
                        )
                )
            )
            .leftJoin(this.tables.address()).on(
                this.tables.address().id.eq(this.tables.home().id.locatorUid)
            )
            .leftJoin(this.tables.state()).on(
                this.tables.state().id.eq(this.tables.address().stateCd)
            )
            .leftJoin(this.tables.country()).on(
                this.tables.country().id.eq(this.tables.address().cntryCd)
            )
            //  Most effective Phone number for the as of
            .leftJoin(this.tables.phone()).on(
                this.tables.phone().id.entityUid.eq(this.tables.patient().id),
                this.tables.phone().recordStatusCd.eq(ACTIVE_CODE),
                this.tables.phone().cd.ne(EMAIL_CODE),
                this.tables.phone().asOfDate.eq(
                    JPAExpressions.select(EFF_PHONE.asOfDate.max())
                        .from(EFF_PHONE)
                        .join(EFF_PHONE_CHILD).on(
                            EFF_PHONE_CHILD.id.eq(EFF_PHONE.id.entityUid),
                            EFF_PHONE_CHILD.personParentUid.id.eq(this.tables.patient().personParentUid.id)
                        )
                        .where(
                            EFF_PHONE.id.entityUid.eq(this.tables.phone().id.entityUid),
                            EFF_PHONE.cd.eq(this.tables.phone().cd),
                            EFF_PHONE.asOfDate.loe(asOf)
                        )
                )
            )
            .leftJoin(this.tables.phoneNumber()).on(
                this.tables.phoneNumber().id.eq(this.tables.phone().id.locatorUid),
                this.tables.phoneNumber().phoneNbrTxt.isNotNull()
            )
            .leftJoin(this.tables.phoneUse()).on(
                this.tables.phoneUse().id.codeSetNm.eq("EL_USE_TELE_PAT"),
                this.tables.phoneUse().id.code.eq(this.tables.phone().useCd)
            )
            //  Most effective email for the as of
            .leftJoin(this.tables.net()).on(
                this.tables.net().id.entityUid.eq(this.tables.patient().id),
                this.tables.net().recordStatusCd.eq(ACTIVE_CODE),
                this.tables.net().cd.eq(EMAIL_CODE),
                this.tables.net().asOfDate.eq(
                    JPAExpressions.select(EFF_EMAIL.asOfDate.max())
                        .from(EFF_EMAIL)
                        //  email addresses associated with a child patient have their own as of dates
                        .join(EFF_EMAIL_CHILD).on(
                            EFF_EMAIL_CHILD.id.eq(EFF_EMAIL.id.entityUid),
                            EFF_EMAIL_CHILD.personParentUid.id.eq(this.tables.patient().personParentUid.id)
                        )
                        .where(
                            EFF_EMAIL.id.entityUid.eq(this.tables.net().id.entityUid),
                            EFF_EMAIL.cd.eq(this.tables.net().cd),
                            EFF_EMAIL.asOfDate.loe(asOf)
                        )
                )
            )
            .leftJoin(this.tables.email()).on(
                this.tables.email().id.eq(this.tables.net().id.locatorUid),
                this.tables.email().emailAddress.isNotNull()
            )
            .leftJoin(this.tables.emailUse()).on(
                this.tables.emailUse().id.codeSetNm.eq("EL_USE_TELE_PAT"),
                this.tables.emailUse().id.code.eq(this.tables.net().useCd)
            )
            .where(this.tables.patient().id.eq(identifier), this.tables.patient().cd.eq(PATIENT_CODE))
            .fetch()
            .stream()
            .map(mapper::map)
            .collect(Accumulator.accumulating(PatientSummary::patient, this.merger::merge))
            ;
    }
}

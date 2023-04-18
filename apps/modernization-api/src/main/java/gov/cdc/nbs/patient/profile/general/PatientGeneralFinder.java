package gov.cdc.nbs.patient.profile.general;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.enums.RecordStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class PatientGeneralFinder {

    private static final String PATIENT_CODE = "PAT";

    private final JPAQueryFactory factory;
    private final PatientGeneralTupleMapper.Tables tables;
    private final PatientGeneralTupleMapper mapper;

    PatientGeneralFinder(final JPAQueryFactory factory) {
        this.factory = factory;
        this.tables = new PatientGeneralTupleMapper.Tables();
        this.mapper = new PatientGeneralTupleMapper(tables);
    }

    Optional<PatientGeneral> find(final long patient) {
        return this.factory.select(
                tables.patient().personParentUid.id,
                tables.patient().id,
                tables.patient().versionCtrlNbr,
                tables.patient().asOfDateGeneral,
                tables.maritalStatus().id.code,
                tables.maritalStatus().codeShortDescTxt,
                tables.patient().mothersMaidenNm,
                tables.patient().adultsInHouseNbr,
                tables.patient().childrenInHouseNbr,
                tables.occupation().id,
                tables.occupation().codeShortDescTxt,
                tables.education().id.code,
                tables.education().codeShortDescTxt,
                tables.language().id,
                tables.language().codeShortDescTxt,
                tables.patient().speaksEnglishCd,
                tables.patient().eharsId
            ).from(this.tables.patient())
            .leftJoin(this.tables.maritalStatus()).on(
                this.tables.maritalStatus().id.codeSetNm.eq("P_MARITAL"),
                this.tables.maritalStatus().id.code.eq(tables.patient().maritalStatusCd)
            )
            .leftJoin(this.tables.occupation()).on(
                this.tables.occupation().id.eq(this.tables.patient().occupationCd)
            )
            .leftJoin(this.tables.education()).on(
                this.tables.education().id.codeSetNm.eq("P_EDUC_LVL"),
                this.tables.education().id.code.eq(this.tables.patient().educationLevelCd)
            )
            .leftJoin(this.tables.language()).on(
                this.tables.language().id.eq(this.tables.patient().primLangCd)
            )
            .where(
                this.tables.patient().id.eq(patient),
                this.tables.patient().cd.eq(PATIENT_CODE),
                this.tables.patient().recordStatusCd.eq(RecordStatus.ACTIVE),
                this.tables.patient().asOfDateGeneral.isNotNull()
            )
            .stream()
            .map(mapper::map)
            .findFirst();
    }
}

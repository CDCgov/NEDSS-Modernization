package gov.cdc.nbs.patient.profile.gender;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.enums.RecordStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class PatientGenderFinder {
    private static final String PATIENT_CODE = "PAT";
    private static final String UNKNOWN_GENDER_REASON = "SEX_UNK_REASON";
    private static final String PREFERRED_GENDER_CODE = "NBS_STD_GENDER_PARPT";

    private final JPAQueryFactory factory;
    private final PatientGenderTupleMapper.Tables tables;
    private final PatientGenderTupleMapper mapper;

    PatientGenderFinder(final JPAQueryFactory factory) {
        this.factory = factory;
        this.tables = new PatientGenderTupleMapper.Tables();
        mapper = new PatientGenderTupleMapper(tables);
    }

    Optional<PatientGender> find(final long patient) {
        return this.factory.select(
                tables.patient().personParentUid.id,
                tables.patient().id,
                tables.patient().versionCtrlNbr,
                tables.patient().asOfDateSex,
                tables.patient().currSexCd,
                tables.patient().birthGenderCd,
                tables.patient().sexUnkReasonCd,
                tables.unknownGenderReason().codeShortDescTxt,
                tables.patient().preferredGenderCd,
                tables.preferred().codeShortDescTxt,
                tables.patient().additionalGenderCd
            )
            .from(this.tables.patient())
            .leftJoin(tables.unknownGenderReason()).on(
                tables.unknownGenderReason().id.codeSetNm.eq(UNKNOWN_GENDER_REASON),
                tables.unknownGenderReason().id.code.eq(tables.patient().sexUnkReasonCd)
            )
            .leftJoin(tables.preferred()).on(
                tables.preferred().id.codeSetNm.eq(PREFERRED_GENDER_CODE),
                tables.preferred().id.code.eq(tables.patient().preferredGenderCd)
            )
            .where(
                this.tables.patient().id.eq(patient),
                this.tables.patient().cd.eq(PATIENT_CODE),
                this.tables.patient().recordStatusCd.eq(RecordStatus.ACTIVE),
                this.tables.patient().asOfDateSex.isNotNull()
            )
            .fetch()
            .stream()
            .map(mapper::map)
            .findFirst();
    }
}

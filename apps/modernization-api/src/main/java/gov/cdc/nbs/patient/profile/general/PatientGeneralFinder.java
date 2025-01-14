package gov.cdc.nbs.patient.profile.general;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.data.sensitive.SensitiveValueResolver;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class PatientGeneralFinder {

  private static final String PATIENT_CODE = "PAT";

  private final JPAQueryFactory factory;
  private final PatientGeneralTupleMapper.Tables tables;
  private final PatientGeneralTupleMapper mapper;

  PatientGeneralFinder(
      final JPAQueryFactory factory,
      final SensitiveValueResolver resolver
  ) {
    this.factory = factory;
    this.tables = new PatientGeneralTupleMapper.Tables();
    this.mapper = new PatientGeneralTupleMapper(tables, resolver);
  }

  Optional<PatientGeneral> find(final long patient) {
    return this.factory.select(
            tables.patient().personParentUid.id,
            tables.patient().id,
            tables.patient().versionCtrlNbr,
            tables.patient().generalInformation.asOf,
            tables.maritalStatus().id.code,
            tables.maritalStatus().codeShortDescTxt,
            tables.patient().generalInformation.mothersMaidenName,
            tables.patient().generalInformation.adultsInHouse,
            tables.patient().generalInformation.childrenInHouse,
            tables.occupation().id,
            tables.occupation().codeShortDescTxt,
            tables.education().id.code,
            tables.education().codeShortDescTxt,
            tables.language().id,
            tables.language().codeShortDescTxt,
            tables.patient().generalInformation.speaksEnglish,
            tables.patient().generalInformation.stateHIVCase
        ).from(this.tables.patient())
        .leftJoin(this.tables.maritalStatus()).on(
            this.tables.maritalStatus().id.codeSetNm.eq("P_MARITAL"),
            this.tables.maritalStatus().id.code.eq(tables.patient().generalInformation.maritalStatus)
        )
        .leftJoin(this.tables.occupation()).on(
            this.tables.occupation().id.eq(this.tables.patient().generalInformation.occupation)
        )
        .leftJoin(this.tables.education()).on(
            this.tables.education().id.codeSetNm.eq("P_EDUC_LVL"),
            this.tables.education().id.code.eq(this.tables.patient().generalInformation.educationLevel)
        )
        .leftJoin(this.tables.language()).on(
            this.tables.language().id.eq(this.tables.patient().generalInformation.primaryLanguage)
        )
        .where(
            this.tables.patient().id.eq(patient),
            this.tables.patient().cd.eq(PATIENT_CODE),
            this.tables.patient().generalInformation.asOf.isNotNull()
        )
        .stream()
        .map(mapper::map)
        .findFirst();
  }
}

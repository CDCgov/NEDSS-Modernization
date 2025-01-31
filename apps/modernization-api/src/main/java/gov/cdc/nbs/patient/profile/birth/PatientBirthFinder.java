package gov.cdc.nbs.patient.profile.birth;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.odse.QPostalEntityLocatorParticipation;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class PatientBirthFinder {

  private static final QPostalEntityLocatorParticipation LOCATORS =
      QPostalEntityLocatorParticipation.postalEntityLocatorParticipation;

  private static final String PATIENT_CODE = "PAT";
  private static final String BIRTH_ADDRESS_CODE = "BIR";
  private static final String ACTIVE_CODE = "ACTIVE";

  private final JPAQueryFactory factory;
  private final PatientBirthTupleMapper.Tables tables;
  private final PatientBirthTupleMapper mapper;

  PatientBirthFinder(final JPAQueryFactory factory) {
    this.factory = factory;
    this.tables = new PatientBirthTupleMapper.Tables();
    this.mapper = new PatientBirthTupleMapper(tables);
  }

  Optional<PatientBirth> find(final long patient) {
    return this.factory.select(
            tables.patient().personParentUid.id,
            tables.patient().id,
            tables.patient().versionCtrlNbr,
            tables.patient().asOfDateSex,
            tables.patient().birthTime,
            tables.patient().multipleBirthInd,
            tables.patient().birthOrderNbr,
            tables.multipleBirth().codeShortDescTxt,
            tables.address().cityDescTxt,
            tables.address().stateCd,
            tables.state().stateNm,
            tables.address().cntyCd,
            tables.county().codeDescTxt,
            tables.address().cntryCd,
            tables.country().codeDescTxt
        ).from(this.tables.patient())
        .leftJoin(this.tables.multipleBirth()).on(
            this.tables.multipleBirth().id.codeSetNm.eq("YNU"),
            this.tables.multipleBirth().id.code.eq(this.tables.patient().multipleBirthInd)
        )
        .leftJoin(LOCATORS).on(
            LOCATORS.id.entityUid.eq(this.tables.patient().id),
            LOCATORS.useCd.eq(BIRTH_ADDRESS_CODE),
            LOCATORS.recordStatus.status.eq(ACTIVE_CODE)
        )
        .leftJoin(this.tables.address()).on(
            this.tables.address().id.eq(LOCATORS.id.locatorUid)
        )
        .leftJoin(this.tables.state()).on(
            this.tables.state().id.eq(this.tables.address().stateCd)
        )
        .leftJoin(this.tables.county()).on(
            this.tables.county().id.eq(this.tables.address().cntyCd)
        )
        .leftJoin(this.tables.country()).on(
            this.tables.country().id.eq(this.tables.address().cntryCd)
        )
        .where(
            this.tables.patient().id.eq(patient),
            this.tables.patient().cd.eq(PATIENT_CODE),
            this.tables.patient().asOfDateSex.isNotNull()
        )
        .stream()
        .map(mapper::map)
        .findFirst();
  }
}

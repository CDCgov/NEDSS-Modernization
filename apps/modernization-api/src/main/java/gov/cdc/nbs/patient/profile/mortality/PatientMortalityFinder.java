package gov.cdc.nbs.patient.profile.mortality;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class PatientMortalityFinder {

  private static final String PATIENT_CODE = "PAT";
  private static final String DEATH_ADDRESS_CODE = "DTH";
  private static final String ACTIVE_CODE = "ACTIVE";
  private final JPAQueryFactory factory;
  private final PatientMortalityTupleMapper.Tables tables;
  private final PatientMortalityTupleMapper mapper;

  PatientMortalityFinder(final JPAQueryFactory factory) {
    this.factory = factory;
    this.tables = new PatientMortalityTupleMapper.Tables();
    this.mapper = new PatientMortalityTupleMapper(tables);
  }

  Optional<PatientMortality> find(final long patient) {
    return this.factory.select(
            tables.patient().personParentUid.id,
            tables.patient().id,
            tables.patient().versionCtrlNbr,
            tables.patient().asOfDateMorbidity,
            tables.patient().deceasedIndCd,
            tables.patient().deceasedTime,
            tables.address().cityDescTxt,
            tables.address().stateCd,
            tables.state().stateNm,
            tables.address().cntyCd,
            tables.county().codeDescTxt,
            tables.address().cntryCd,
            tables.country().codeDescTxt
        ).from(this.tables.patient())
        .leftJoin(this.tables.locators()).on(
            this.tables.locators().id.entityUid.eq(this.tables.patient().id),
            this.tables.locators().useCd.eq(DEATH_ADDRESS_CODE),
            this.tables.locators().recordStatus.status.eq(ACTIVE_CODE)
        )
        .leftJoin(this.tables.address()).on(
            this.tables.address().id.eq(this.tables.locators().id.locatorUid)
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
            this.tables.patient().asOfDateMorbidity.isNotNull()
        )
        .stream()
        .map(mapper::map)
        .findFirst();
  }
}

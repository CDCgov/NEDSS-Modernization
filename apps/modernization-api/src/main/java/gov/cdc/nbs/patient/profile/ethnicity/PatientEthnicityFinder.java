package gov.cdc.nbs.patient.profile.ethnicity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.accumulation.Accumulator;
import gov.cdc.nbs.entity.odse.QPersonEthnicGroup;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class PatientEthnicityFinder {

  private static final String PATIENT_CODE = "PAT";
  private static final String ETHNIC_GROUP_CODE_SET = "PHVS_ETHNICITYGROUP_CDC_UNK";
  private static final QPersonEthnicGroup DETAILED = QPersonEthnicGroup.personEthnicGroup;
  private static final String UNKNOWN_REASON_CODE_SET = "P_ETHN_UNK_REASON";
  private static final String ETHNICITY_CODE_SET = "P_ETHN";
  private final JPAQueryFactory factory;

  private final PatientEthnicityTupleMapper.Tables tables;
  private final PatientEthnicityTupleMapper mapper;

  private final PatientEthnicityMerger merger;

  PatientEthnicityFinder(final JPAQueryFactory factory) {
    this.factory = factory;
    this.tables = new PatientEthnicityTupleMapper.Tables();
    this.mapper = new PatientEthnicityTupleMapper(tables);
    this.merger = new PatientEthnicityMerger();
  }

  Optional<PatientEthnicity> find(final long patient) {
    return this.factory.select(
            tables.patient().personParentUid.id,
            tables.patient().id,
            tables.patient().versionCtrlNbr,
            tables.patient().ethnicity.asOfDateEthnicity,
            tables.ethnicGroup().id.code,
            tables.ethnicGroup().codeShortDescTxt,
            tables.unknownReason().id.code,
            tables.unknownReason().codeShortDescTxt,
            tables.ethnicity().id.code,
            tables.ethnicity().codeShortDescTxt
        )
        .from(this.tables.patient())
        .where(
            this.tables.patient().id.eq(patient),
            this.tables.patient().cd.eq(PATIENT_CODE),
            this.tables.patient().ethnicity.asOfDateEthnicity.isNotNull()
        )
        .leftJoin(this.tables.ethnicGroup()).on(
            this.tables.ethnicGroup().id.codeSetNm.eq(ETHNIC_GROUP_CODE_SET),
            this.tables.ethnicGroup().id.code.eq(this.tables.patient().ethnicity.ethnicGroupInd)
        )
        .leftJoin(this.tables.unknownReason()).on(
            this.tables.unknownReason().id.codeSetNm.eq(UNKNOWN_REASON_CODE_SET),
            this.tables.unknownReason().id.code.eq(this.tables.patient().ethnicity.ethnicUnkReasonCd)
        )
        .leftJoin(DETAILED).on(
            DETAILED.id.personUid.eq(this.tables.patient().id)
        )
        .leftJoin(this.tables.ethnicity()).on(
            this.tables.ethnicity().id.codeSetNm.eq(ETHNICITY_CODE_SET),
            this.tables.ethnicity().id.code.eq(DETAILED.id.ethnicGroupCd)
        )
        .fetch()
        .stream()
        .map(mapper::map)
        .collect(Accumulator.collecting(PatientEthnicity::patient, this.merger::merge))
        .stream()
        .findFirst();
  }


}

package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.entity.odse.Person;

import java.util.Objects;

class PatientChangeHash {

  static long compute(final Person patient) {
    return Objects.hash(
        //  administrative
        patient.getAsOfDateAdmin(),
        patient.getDescription(),
        //  general information
        patient.getAsOfDateGeneral(),
        patient.getMaritalStatusCd(),
        patient.getMothersMaidenNm(),
        patient.getAdultsInHouseNbr(),
        patient.getChildrenInHouseNbr(),
        patient.getOccupationCd(),
        patient.getEducationLevelCd(),
        patient.getPrimLangCd(),
        patient.getSpeaksEnglishCd(),
        patient.getEharsId(),
        //  mortality
        patient.getAsOfDateMorbidity(),
        patient.getDeceasedIndCd(),
        patient.getDeceasedTime(),
        //  ethnicity
        patient.getEthnicity().asOf(),
        patient.getEthnicity().unknownReason(),
        //  Sex & birth
        patient.getAsOfDateSex(),
        patient.getBirthTime(),
        patient.getCurrSexCd(),
        patient.getSexUnkReasonCd(),
        patient.getPreferredGenderCd(),
        patient.getAdditionalGenderCd(),
        patient.getBirthGenderCd(),
        patient.getMultipleBirthInd(),
        patient.getBirthOrderNbr()
    );
  }

  private PatientChangeHash() {
    //  NOOP
  }
}

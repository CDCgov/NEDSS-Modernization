package gov.cdc.nbs.questionbank.support.condition;

import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;
import java.time.Instant;

public class ConditionEntityMother {
  private ConditionEntityMother() {}

  public static ConditionCode conditionCode() {
    Instant now = Instant.now();
    ConditionCode c = new ConditionCode();
    c.setId("T1234567");
    c.setConditionCodesetNm("PHC_TYPE");
    c.setConditionSeqNum((short) 1);
    c.setAssigningAuthorityCd("2.16.840.1.114222");
    c.setAssigningAuthorityDescTxt("Centers for Disease Control");
    c.setCodeSystemCd("2.16.840.1.114222.4.5.277");
    c.setCodeSystemDescTxt("Notifiable Event Code List");
    c.setConditionDescTxt("Sample Text");
    c.setConditionShortNm("Sample Text");
    c.setEffectiveFromTime(now);
    c.setEffectiveToTime(now);
    c.setIndentLevelNbr((short) 1);
    c.setInvestigationFormCd("INV_FORM_GEN");
    c.setIsModifiableInd('Y');
    c.setNbsUid(999L);
    c.setNndInd('Y');
    c.setParentIsCd("99999");
    c.setProgAreaCd("ARBO");
    c.setReportableMorbidityInd('Y');
    c.setReportableSummaryInd('Y');
    c.setStatusCd('A');
    c.setStatusTime(now);
    c.setNndEntityIdentifier("Arbo_Case");
    c.setSummaryInvestigationFormCd("sample");
    c.setContactTracingEnableInd('Y');
    c.setVaccineEnableInd('Y');
    c.setTreatmentEnableInd('Y');
    c.setLabReportEnableInd('Y');
    c.setMorbReportEnableInd('Y');
    c.setPortReqIndCd('Y');
    c.setFamilyCd("ARBO");
    c.setCoinfectionGrpCd("STD_HIV_GROUP");
    c.setRhapParseNbsInd("N");
    c.setRhapActionValue("N");

    return c;
  }
}

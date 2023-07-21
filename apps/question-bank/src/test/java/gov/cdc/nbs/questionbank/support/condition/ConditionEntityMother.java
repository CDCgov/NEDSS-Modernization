package gov.cdc.nbs.questionbank.support.condition;

import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;

import javax.persistence.Column;
import java.time.Instant;

public class ConditionEntityMother {
    private ConditionEntityMother(){}

    public static ConditionCode conditionCode() {
        Instant now = Instant.now();
        ConditionCode c = new ConditionCode();
        c.setId("1L");
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
        c.setInvestigationFormCd("PG_Arboviral_Human_Investigation");
        c.setIsModifiableInd('Y');
        c.setNbsUid(1L);
        c.setNndInd('Y');
        c.setParentIsCd("sample");
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
        c.setPortReqIndCd("sample port req");
        c.setFamilyCd("ARBO");
        c.setCoinfectionGrpCd("sample co infection cd");
        c.setRhapParseNbsInd("sample rhap parse");
        c.setRhapActionValue("sample rhap action");


        return c;
    }
}

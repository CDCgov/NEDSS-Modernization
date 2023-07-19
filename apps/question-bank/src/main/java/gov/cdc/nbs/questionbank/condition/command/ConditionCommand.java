package gov.cdc.nbs.questionbank.condition.command;
import java.time.Instant;

public sealed interface ConditionCommand {
    public record AddCondition(
        String id,
        String conditionCodesetNm,
        Integer conditionSeqNum,
        String assigningAuthorityCd,
        String assigningAuthorityDescTxt,
        String codeSystemCd,
        String codeSystemDescTxt,
        String conditionDescTxt,
        String conditionShortNm,
        Instant effectiveFromTime,
        Instant effectiveToTime,
        Integer indentLevelNbr,
        String investigationFormCd,
        String isModifiableInd,
        Integer nbsUid,
        String nndInd,
        String parentIsCd,
        String progAreaCd,
        String reportableMorbidityInd,
        String reportableSummaryInd,
        String statusCd,
        Instant statusTime,
        String nndEntityIdentifier,
        String nndSummaryEntityIdentifier,
        String summaryInvestigationFormCd,
        String contactTracingEnableInd,
        String vaccineEnableInd,
        String treatmentEnableInd,
        String labReportEnableInd,
        String morbReportEnableInd,
        String portReqIndCd,
        String familyCd,
        String coinfectionGrpCd,
        String rhapParseNbsInd,
        String rhapActionValue
    ) implements ConditionCommand {}

}
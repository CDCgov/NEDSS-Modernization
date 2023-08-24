package gov.cdc.nbs.questionbank.condition.request;

public record CreateConditionRequest(
        String code,
        String codeSystemDescTxt,
        String conditionShortNm,
        String progAreaCd,
        Character nndInd,
        Character reportableMorbidityInd,
        Character reportableSummaryInd,
        Character contactTracingEnableInd,
        String familyCd,
        String coinfectionGrpCd

) {
}

package gov.cdc.nbs.questionbank.condition.model;

public record Condition(
        String id,
        String conditionShortNm,
        String progAreaCd,
        String familyCd,
        String coinfectionGrpCd,
        Character nndInd,
        String investigationFormCd,
        Character statusCd) {
}


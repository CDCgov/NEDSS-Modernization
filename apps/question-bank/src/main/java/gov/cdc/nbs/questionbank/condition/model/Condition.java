package gov.cdc.nbs.questionbank.condition.model;

import io.swagger.annotations.ApiModelProperty;

public record Condition(
        @ApiModelProperty(required = true) String id,
        String conditionShortNm,
        String progAreaCd,
        String familyCd,
        String coinfectionGrpCd,
        Character nndInd,
        String investigationFormCd,
        Character statusCd) {
}


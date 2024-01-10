package gov.cdc.nbs.questionbank.condition.request;

import io.swagger.annotations.ApiModelProperty;

public record CreateConditionRequest(
    @ApiModelProperty(required = true) String code,
    @ApiModelProperty(required = true) String codeSystemDescTxt,
    @ApiModelProperty(required = true) String conditionShortNm,
    @ApiModelProperty(required = true) String progAreaCd,
    Character nndInd,
    Character reportableMorbidityInd,
    Character reportableSummaryInd,
    Character contactTracingEnableInd,
    String familyCd,
    String coinfectionGrpCd

) {
}

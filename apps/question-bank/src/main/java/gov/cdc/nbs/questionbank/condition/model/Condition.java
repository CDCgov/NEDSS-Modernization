package gov.cdc.nbs.questionbank.condition.model;

import io.swagger.annotations.ApiModelProperty;

public record Condition(
    @ApiModelProperty(required = true) String id,
    @ApiModelProperty(required = true) String name,
    @ApiModelProperty(required = true) String programArea,
    String conditionFamily,
    String coinfectionGroup,
    Character nndInd,
    String page,
    @ApiModelProperty(required = true) Character status) {
}


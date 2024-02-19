package gov.cdc.nbs.questionbank.valueset.model;

import io.swagger.annotations.ApiModelProperty;

public record Valueset(
    @ApiModelProperty(required = true) Long id,
    @ApiModelProperty(required = true) String type,
    @ApiModelProperty(required = true) String code,
    @ApiModelProperty(required = true) String name,
    String description) {

}

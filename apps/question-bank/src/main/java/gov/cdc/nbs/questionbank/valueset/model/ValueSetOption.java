package gov.cdc.nbs.questionbank.valueset.model;

import io.swagger.annotations.ApiModelProperty;

public record ValueSetOption(
    @ApiModelProperty(required = true) Long id,
    @ApiModelProperty(required = true) String value,
    @ApiModelProperty(required = true) String name,
    @ApiModelProperty(required = true) String codeSetNm,
    String description,
    @ApiModelProperty(required = true) String type) {

}

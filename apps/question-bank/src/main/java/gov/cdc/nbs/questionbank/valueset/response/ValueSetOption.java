package gov.cdc.nbs.questionbank.valueset.response;

import io.swagger.annotations.ApiModelProperty;

public record ValueSetOption(
    @ApiModelProperty(required = true) String name,
    @ApiModelProperty(required = true) String value,
    @ApiModelProperty(required = true) String codeSetNm) {

}

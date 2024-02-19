package gov.cdc.nbs.questionbank.valueset.request;

import io.swagger.annotations.ApiModelProperty;

public record CreateValuesetRequest(
    @ApiModelProperty(required = true) String type,
    @ApiModelProperty(required = true) String code,
    @ApiModelProperty(required = true) String name,
    String description) {

}

package gov.cdc.nbs.questionbank.valueset.request;

import io.swagger.annotations.ApiModelProperty;

public record ValueSetSearchRequest(
    @ApiModelProperty(required = true) String query) {
}



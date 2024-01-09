package gov.cdc.nbs.questionbank.page.request;

import io.swagger.annotations.ApiModelProperty;

public record PageValidationRequest(@ApiModelProperty(required = true) String name) {

}

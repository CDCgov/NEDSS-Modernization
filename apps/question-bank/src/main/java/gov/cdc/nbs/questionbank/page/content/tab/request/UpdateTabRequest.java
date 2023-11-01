package gov.cdc.nbs.questionbank.page.content.tab.request;

import io.swagger.annotations.ApiModelProperty;

public record UpdateTabRequest(
        @ApiModelProperty(required = true) String name,
        @ApiModelProperty(required = true) boolean visible) {

}

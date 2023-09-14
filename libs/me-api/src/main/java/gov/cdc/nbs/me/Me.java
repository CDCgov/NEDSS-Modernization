package gov.cdc.nbs.me;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

record Me(
    @ApiModelProperty(required = true)
    long identifier,
    @ApiModelProperty(required = true)
    String firstName,
    @ApiModelProperty(required = true)
    String lastName,
    @ApiModelProperty(required = true)
    List<String> permissions) {
}

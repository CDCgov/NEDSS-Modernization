package gov.cdc.nbs.authentication.login;

import io.swagger.annotations.ApiModelProperty;


record LoginResponse(
    @ApiModelProperty(required = true)
    long identifier,
    @ApiModelProperty(required = true)
    String username,
    @ApiModelProperty(required = true)
    String displayName,
    @ApiModelProperty(required = true)
    String token
) {
}

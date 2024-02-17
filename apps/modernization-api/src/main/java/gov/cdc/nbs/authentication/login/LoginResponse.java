package gov.cdc.nbs.authentication.login;

import io.swagger.annotations.ApiModelProperty;


record LoginResponse(
    @ApiModelProperty(required = true)
    String username,
    @ApiModelProperty(required = true)
    String token
) {
}

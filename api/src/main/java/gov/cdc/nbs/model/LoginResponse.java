package gov.cdc.nbs.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    @ApiModelProperty(required = true)
    private String username;
    @ApiModelProperty(required = true)
    private String displayName;
    @ApiModelProperty(required = true)
    private String token;
}

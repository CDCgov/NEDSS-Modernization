package gov.cdc.nbs.configuration.nbs;

import java.util.List;
import java.util.Map;
import io.swagger.annotations.ApiModelProperty;

public record Properties(
    @ApiModelProperty(required = true) List<String> stdProgramAreas,
    @ApiModelProperty(required = true) List<String> hivProgramAreas,
    @ApiModelProperty(required = true) Map<String, String> entries) {
}

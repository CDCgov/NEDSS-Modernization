package gov.cdc.nbs.questionbank.valueset.request;

import java.time.Instant;
import io.swagger.annotations.ApiModelProperty;

public record UpdateConceptRequest(
        @ApiModelProperty(required = true) String name,
        @ApiModelProperty(required = true) String displayName,
        @ApiModelProperty(required = true) Instant effectiveFromTime,
        Instant effectiveToTime,
        @ApiModelProperty(required = true) boolean active,
        String adminComments) {

}

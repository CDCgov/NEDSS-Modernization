package gov.cdc.nbs.questionbank.valueset.request;

import java.time.Instant;
import io.swagger.annotations.ApiModelProperty;

public record UpdateConceptRequest(
        @ApiModelProperty(required = true) String name,
        @ApiModelProperty(required = true) String displayName,
        @ApiModelProperty(required = true) Instant effectiveFromTime,
        Instant effectiveToTime,
        @ApiModelProperty(required = true) boolean active,
        String adminComments,
        ConceptMessagingInfo conceptMessagingInfo) {

    public record ConceptMessagingInfo(
            @ApiModelProperty(required = true) String conceptCode,
            @ApiModelProperty(required = true) String conceptName,
            @ApiModelProperty(required = true) String preferredConceptName,
            @ApiModelProperty(required = true) String codeSystem) {
    }

}

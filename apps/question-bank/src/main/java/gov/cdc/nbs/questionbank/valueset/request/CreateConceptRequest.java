package gov.cdc.nbs.questionbank.valueset.request;

import java.time.Instant;
import gov.cdc.nbs.questionbank.valueset.model.Concept.Status;
import io.swagger.annotations.ApiModelProperty;

public record CreateConceptRequest(
    @ApiModelProperty(required = true) String localCode,
    @ApiModelProperty(required = true) String longName,
    @ApiModelProperty(required = true) String display,
    Instant effectiveFromTime,
    Instant effectiveToTime,
    @ApiModelProperty(required = true) Status status,
    String adminComments,
    // Messaging
    @ApiModelProperty(required = true) String conceptCode,
    @ApiModelProperty(required = true) String conceptName,
    @ApiModelProperty(required = true) String preferredConceptName,
    @ApiModelProperty(required = true) String codeSystem) {
}



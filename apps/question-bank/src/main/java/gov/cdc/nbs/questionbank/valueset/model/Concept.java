package gov.cdc.nbs.questionbank.valueset.model;

import java.time.Instant;
import io.swagger.annotations.ApiModelProperty;

public record Concept(
    @ApiModelProperty(required = true) String codeSetName,
    @ApiModelProperty(required = true) String localCode,
    @ApiModelProperty(required = true) String longName,
    @ApiModelProperty(required = true) String display,
    @ApiModelProperty(required = true) Instant effectiveFromTime,
    Instant effectiveToTime,
    @ApiModelProperty(required = true) Status status,
    String adminComments,
    // Messaging
    @ApiModelProperty(required = true) String conceptCode,
    @ApiModelProperty(required = true) String conceptName,
    @ApiModelProperty(required = true) String preferredConceptName,
    @ApiModelProperty(required = true) String codeSystem) {

  public enum Status {
    ACTIVE,
    INACTIVE
  }
}

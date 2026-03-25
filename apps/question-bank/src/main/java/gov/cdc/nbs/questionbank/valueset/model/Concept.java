package gov.cdc.nbs.questionbank.valueset.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

public record Concept(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String codeSetName,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String localCode,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String longName,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String display,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Instant effectiveFromTime,
    Instant effectiveToTime,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Status status,
    String adminComments,
    // Messaging
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String conceptCode,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String conceptName,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String preferredConceptName,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String codeSystem) {

  public enum Status {
    ACTIVE,
    INACTIVE
  }
}

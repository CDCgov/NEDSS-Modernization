package gov.cdc.nbs.questionbank.valueset.request;

import gov.cdc.nbs.questionbank.valueset.model.Concept.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

public record CreateConceptRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String localCode,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String longName,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String display,
    Instant effectiveFromTime,
    Instant effectiveToTime,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Status status,
    String adminComments,
    // Messaging
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String conceptCode,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String conceptName,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String preferredConceptName,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String codeSystem) {}

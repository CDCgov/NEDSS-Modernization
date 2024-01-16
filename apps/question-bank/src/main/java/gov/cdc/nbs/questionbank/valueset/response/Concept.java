package gov.cdc.nbs.questionbank.valueset.response;

import java.time.Instant;

public record Concept(
    String localCode,
    String codeSetName,
    String display,
    String longName,
    String conceptCode,
    String preferredConceptName,
    String codeSystem,
    String status,
    Instant effectiveFromTime,
    Instant effectiveToTime) {
}

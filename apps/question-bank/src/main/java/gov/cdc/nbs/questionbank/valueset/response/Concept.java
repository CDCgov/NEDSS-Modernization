package gov.cdc.nbs.questionbank.valueset.response;

import java.time.Instant;

public record Concept(
        String localCode,
        String codesetName,
        String display,
        String conceptCode,
        String messagingConceptName,
        String codeSystem,
        String status,
        Instant effectiveFromTime,
        Instant effectiveToTime) {

}

package gov.cdc.nbs.questionbank.valueset.command;

import java.time.Instant;
import gov.cdc.nbs.questionbank.valueset.request.AddConceptRequest.StatusCode;

public sealed interface ConceptCommand {
    long userId();

    Instant requestedOn();

    public record AddConcept(
            String codeset,
            String code,
            String displayName,
            String shortDisplayName,
            Instant effectiveFromTime,
            Instant effectiveToTime,
            StatusCode statusCode,
            String adminComments,
            String conceptTypeCd,

            // Messaging fields
            String conceptCode,
            String conceptName,
            String preferredConceptName,
            String codeSystem,
            String codeSystemId,
            // Audit fields
            long userId,
            Instant requestedOn) implements ConceptCommand {
    }

    public record UpdateConcept(
        String longDisplayName,
        String shortDisplayName,
        Instant effectiveFromTime,
        Instant effectiveToTime,
        boolean active,
        String adminComments,
        Instant conceptStatusTime,

        // Concept Messaging Info
        String conceptCode,
        String conceptName,
        String preferredConceptName,
        String codeSystem,
        String codeSystemCd,

        // Audit Fields
        long userId,
        Instant requestedOn
    ) implements ConceptCommand {

    }
}

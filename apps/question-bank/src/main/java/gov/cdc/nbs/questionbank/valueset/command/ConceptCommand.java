package gov.cdc.nbs.questionbank.valueset.command;

import java.time.Instant;

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
      Character status,
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
      Instant requestedOn)
      implements ConceptCommand {}

  public record UpdateConcept(
      String displayName,
      String shortDisplayName,
      Instant effectiveFromTime,
      Instant effectiveToTime,
      Character status,
      String adminComments,

      // Messaging fields
      String conceptCode,
      String conceptName,
      String preferredConceptName,
      String codeSystem,
      String codeSystemId,
      // Audit fields
      long userId,
      Instant requestedOn)
      implements ConceptCommand {}
}

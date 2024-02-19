package gov.cdc.nbs.questionbank.valueset.command;

import java.time.Instant;

public sealed interface ValueSetCommand {
  record AddValueSet(
      String type,
      String name,
      String code,
      String description,
      long codesetId,
      Instant addTime,
      long addUserId) implements ValueSetCommand {
  }

}

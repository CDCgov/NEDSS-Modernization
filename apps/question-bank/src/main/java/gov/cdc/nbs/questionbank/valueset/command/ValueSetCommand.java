package gov.cdc.nbs.questionbank.valueset.command;

import java.time.Instant;

public sealed interface ValueSetCommand {
  record Add(
      String type,
      String name,
      String code,
      String description,
      long codesetId,
      Instant addTime,
      long addUserId)
      implements ValueSetCommand {}

  record Update(String name, String description) implements ValueSetCommand {}
}

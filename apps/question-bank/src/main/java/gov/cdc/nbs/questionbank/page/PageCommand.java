package gov.cdc.nbs.questionbank.page;

import java.time.Instant;

public sealed interface PageCommand {
  long requester();

  Instant requestedOn();

  record UpdateInformation(
      String messageMappingGuide, String description, long requester, Instant requestedOn)
      implements PageCommand {}

  record ChangeName(String name, long requester, Instant requestedOn) implements PageCommand {}

  record ChangeDatamart(String datamart, long requester, Instant requestedOn)
      implements PageCommand {}

  record RelateCondition(String condition, long requester, Instant requestedOn)
      implements PageCommand {}

  record DissociateCondition(String condition, long requester, Instant requestedOn)
      implements PageCommand {}

  record Publish(long requester, Instant requestedOn) implements PageCommand {}

  record CreateTemplate(String name, String description, long requester, Instant requestedOn)
      implements PageCommand {}
}

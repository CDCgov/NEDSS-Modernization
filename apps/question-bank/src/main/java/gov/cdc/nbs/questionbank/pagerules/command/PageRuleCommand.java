package gov.cdc.nbs.questionbank.pagerules.command;

import java.time.Instant;
import gov.cdc.nbs.questionbank.pagerules.RuleData;
import gov.cdc.nbs.questionbank.pagerules.request.RuleRequest;

public sealed interface PageRuleCommand {
  long userId();

  long page();

  Instant requestedOn();

  public record AddPageRule(
      RuleData ruleData,
      RuleRequest ruleRequest,
      Instant requestedOn,
      long userId,
      long page) implements PageRuleCommand {
  }
}

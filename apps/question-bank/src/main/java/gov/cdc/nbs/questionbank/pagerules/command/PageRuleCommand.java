package gov.cdc.nbs.questionbank.pagerules.command;

import java.time.Instant;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.pagerules.RuleData;

public sealed interface PageRuleCommand {
        long userId();

        long page();

        Instant requestedOn();

        public record AddPageRule(
                        RuleData ruleData,
                        CreateRuleRequest ruleRequest,
                        Instant requestedOn,
                        long userId,
                        long page) implements PageRuleCommand {
        }
}

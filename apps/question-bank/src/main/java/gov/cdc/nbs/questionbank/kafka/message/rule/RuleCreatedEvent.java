package gov.cdc.nbs.questionbank.kafka.message.rule;

import gov.cdc.nbs.questionbank.model.CreateRuleRequest;


public record RuleCreatedEvent(CreateRuleRequest.ruleRequest ruleCreatedEvent) {
}

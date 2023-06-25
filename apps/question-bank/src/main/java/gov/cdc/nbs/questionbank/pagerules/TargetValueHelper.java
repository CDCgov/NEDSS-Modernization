package gov.cdc.nbs.questionbank.pagerules;

import java.util.List;

public record TargetValueHelper(String targetIdentifiers, List<String> targetValues, String ruleExpression, String errorMsgText, String sourceIdentifier, String sourceText) {

}



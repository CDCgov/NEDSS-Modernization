package gov.cdc.nbs.questionbank.page.request;

import java.util.Set;

public record UpdatePageDetailsRequest(
        String name,
        String messageMappingGuide,
        String dataMartName,
        String description,
        Set<String> conditionIds) {
}

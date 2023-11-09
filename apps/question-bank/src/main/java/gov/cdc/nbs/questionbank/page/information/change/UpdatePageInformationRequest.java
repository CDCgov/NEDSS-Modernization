package gov.cdc.nbs.questionbank.page.information.change;

import java.util.Collection;

public record UpdatePageInformationRequest(
    String messageMappingGuide,
    String name,
    String datamart,
    String description,
    Collection<String> associated
) {
}

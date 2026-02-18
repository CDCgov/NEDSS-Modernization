package gov.cdc.nbs.questionbank.page.information;

import gov.cdc.nbs.questionbank.page.SelectableCondition;
import gov.cdc.nbs.questionbank.page.SelectableEventType;
import gov.cdc.nbs.questionbank.page.SelectableMessageMappingGuide;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;

record PageInformation(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) long page,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) SelectableEventType eventType,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        SelectableMessageMappingGuide messageMappingGuide,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name,
    String datamart,
    String description,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        Collection<SelectableCondition> conditions) {}

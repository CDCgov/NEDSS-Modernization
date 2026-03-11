package gov.cdc.nbs.questionbank.page.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record PageCreateRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "INV") String eventType,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) List<String> conditionIds,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1000375") Long templateId,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Gen_Case_Map_v1.0")
        String messageMappingGuide,
    String pageDescription,
    String dataMartName) {}

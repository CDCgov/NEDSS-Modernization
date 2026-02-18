package gov.cdc.nbs.configuration.nbs;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;

public record Properties(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) List<String> stdProgramAreas,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) List<String> hivProgramAreas,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Map<String, String> entries) {}

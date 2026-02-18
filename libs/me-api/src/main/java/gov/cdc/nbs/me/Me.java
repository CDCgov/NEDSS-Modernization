package gov.cdc.nbs.me;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

record Me(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) long identifier,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String firstName,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String lastName,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) List<String> permissions) {}

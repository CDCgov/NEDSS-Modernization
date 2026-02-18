package gov.cdc.nbs.authentication.login;

import io.swagger.v3.oas.annotations.media.Schema;

record LoginResponse(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String username,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String token) {}

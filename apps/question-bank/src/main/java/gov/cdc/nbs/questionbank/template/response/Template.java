package gov.cdc.nbs.questionbank.template.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

public record Template(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long id,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String templateNm,
    String recordStatusCd,
    Instant lastChgTime,
    Long lastChgUserId,
    String descTxt,
    Long parentTemplateUid,
    String sourceNm) {}

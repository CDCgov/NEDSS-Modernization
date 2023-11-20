package gov.cdc.nbs.questionbank.template.response;

import java.time.Instant;
import io.swagger.annotations.ApiModelProperty;

public record Template(
    @ApiModelProperty(required = true) Long id,
    @ApiModelProperty(required = true) String templateNm,
    String recordStatusCd,
    Instant lastChgTime,
    Long lastChgUserId,
    String descTxt,
    Long parentTemplateUid,
    String sourceNm) {
}

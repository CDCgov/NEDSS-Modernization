package gov.cdc.nbs.questionbank.page.request;

import java.util.List;
import io.swagger.annotations.ApiModelProperty;

public record PageCreateRequest(
        @ApiModelProperty(required = true, example = "INV") String eventType,
        @ApiModelProperty(required = true) List<String> conditionIds,
        @ApiModelProperty(required = true) String name,
        @ApiModelProperty(required = true, example = "1000375") Long templateId,
        @ApiModelProperty(required = true, example = "Gen_Case_Map_v1.0") String messageMappingGuide,
        String pageDescription,
        String dataMartName

) {
}

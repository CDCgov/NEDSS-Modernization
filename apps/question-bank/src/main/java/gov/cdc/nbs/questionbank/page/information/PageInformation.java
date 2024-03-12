package gov.cdc.nbs.questionbank.page.information;

import gov.cdc.nbs.questionbank.page.SelectableCondition;
import gov.cdc.nbs.questionbank.page.SelectableEventType;
import gov.cdc.nbs.questionbank.page.SelectableMessageMappingGuide;
import io.swagger.annotations.ApiModelProperty;
import java.util.Collection;

record PageInformation(
    @ApiModelProperty(required = true) long page,
    @ApiModelProperty(required = true) SelectableEventType eventType,
    @ApiModelProperty(required = true) SelectableMessageMappingGuide messageMappingGuide,
    @ApiModelProperty(required = true) String name,
    String datamart,
    String description,
    @ApiModelProperty(required = true) Collection<SelectableCondition> conditions

) {


}

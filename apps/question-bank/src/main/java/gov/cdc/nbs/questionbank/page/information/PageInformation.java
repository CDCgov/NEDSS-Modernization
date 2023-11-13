package gov.cdc.nbs.questionbank.page.information;

import gov.cdc.nbs.questionbank.page.SelectableCondition;
import gov.cdc.nbs.questionbank.page.SelectableEventType;
import gov.cdc.nbs.questionbank.page.SelectableMessageMappingGuide;

import java.util.Collection;

record PageInformation(
    long page,
    SelectableEventType eventType,
    SelectableMessageMappingGuide messageMappingGuide,
    String name,
    String datamart,
    String description,
    Collection<SelectableCondition> conditions

) {


}

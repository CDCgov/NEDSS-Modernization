package gov.cdc.nbs.questionbank.page.information;

import gov.cdc.nbs.questionbank.page.Condition;
import gov.cdc.nbs.questionbank.page.EventType;
import gov.cdc.nbs.questionbank.page.MessageMappingGuide;

import java.util.Collection;

record PageInformation(
    long page,
    EventType eventType,
    MessageMappingGuide messageMappingGuide,
    String name,
    String datamart,
    String description,
    Collection<Condition> associated

) {


}

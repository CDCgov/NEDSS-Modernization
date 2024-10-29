package gov.cdc.nbs.event.investigation;

import io.cucumber.spring.ScenarioScope;

@ScenarioScope
public record AbcCaseIdentifier(Long identifier, String local) {

}

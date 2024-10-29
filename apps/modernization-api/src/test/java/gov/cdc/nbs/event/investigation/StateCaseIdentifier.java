package gov.cdc.nbs.event.investigation;

import io.cucumber.spring.ScenarioScope;

@ScenarioScope
public record StateCaseIdentifier(Long identifier, String local) {

}

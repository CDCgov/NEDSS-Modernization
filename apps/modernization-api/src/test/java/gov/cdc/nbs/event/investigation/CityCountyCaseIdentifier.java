package gov.cdc.nbs.event.investigation;

import io.cucumber.spring.ScenarioScope;

@ScenarioScope
public record CityCountyCaseIdentifier(Long identifier, String local) {

}

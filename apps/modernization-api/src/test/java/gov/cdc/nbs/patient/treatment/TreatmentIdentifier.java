package gov.cdc.nbs.patient.treatment;

import io.cucumber.spring.ScenarioScope;

@ScenarioScope
public record TreatmentIdentifier(Long identifier, String local) {

}

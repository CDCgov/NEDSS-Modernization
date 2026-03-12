package gov.cdc.nbs.patient.profile.vaccination;

import io.cucumber.spring.ScenarioScope;

@ScenarioScope
public record VaccinationIdentifier(Long identifier, String local) {}

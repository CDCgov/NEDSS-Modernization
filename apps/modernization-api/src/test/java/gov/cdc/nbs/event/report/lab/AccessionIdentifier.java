package gov.cdc.nbs.event.report.lab;

import io.cucumber.spring.ScenarioScope;

@ScenarioScope
public record AccessionIdentifier(Long identifier, String local) {}

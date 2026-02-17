package gov.cdc.nbs.configuration.features.report;

public record Report(
        Execution execution) {

    public record Execution(boolean enabled) {
    }
}

package gov.cdc.nbs.configuration.features.search;

public record Search(
    View view, Events events, Investigations investigations, LaboratoryReports laboratoryReports) {

  public record View(boolean enabled, Table table) {

    record Table(boolean enabled) {}
  }

  public record Events(boolean enabled) {}

  public record Investigations(boolean enabled) {}

  public record LaboratoryReports(boolean enabled) {}
}

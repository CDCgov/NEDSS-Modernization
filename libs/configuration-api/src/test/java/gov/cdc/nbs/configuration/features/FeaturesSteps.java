package gov.cdc.nbs.configuration.features;

import io.cucumber.java.ParameterType;

public class FeaturesSteps {

  @ParameterType(name = "feature", value = ".*")
  public String feature(final String value) {
    return switch (value.toLowerCase()) {
      case "nbs6 event search" -> "features.search.events.enabled";
      case "investigation search" -> "features.search.investigations.enabled";
      case "laboratory report search" -> "features.search.laboratoryReports.enabled";
      case "search view" -> "features.search.view.enabled";
      case "tabular search results" -> "features.search.view.table.enabled";
      case "modernized patient profile" -> "features.patient.profile.enabled";
      case "patient file" -> "features.patient.file.enabled";
      case "patient file merge history" -> "features.patient.file.mergeHistory.enabled";
      case "patient search filters" -> "features.patient.search.filters.enabled";
      case "deduplication" -> "features.deduplication.enabled";
      case "deduplication merge" -> "features.deduplication.merge.enabled";
      case "system management" -> "features.system.management.enabled";
      default -> value;
    };
  }

}

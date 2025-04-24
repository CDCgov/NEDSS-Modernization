package gov.cdc.nbs.configuration.features.deduplication;

public record Deduplication(boolean enabled, Merge merge) {
  public record Merge(boolean enabled) {

  }
}

package gov.cdc.nbs.configuration.features.patient;

public record Patient(Search search, Profile profile, File file) {

  public record Search(Filters filters) {

    public record Filters(boolean enabled) {

    }

  }

  public record Profile(Boolean enabled) {
  }

  public record File(Boolean enabled) {
  }
}

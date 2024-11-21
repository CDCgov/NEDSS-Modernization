package gov.cdc.nbs.configuration.features.patient;

public record Patient(Profile profile, Add add) {

  public record Profile(Boolean enabled) {}

  public record Add(Boolean enabled, Add.Extended extended) {
    public record Extended(Boolean enabled) {
    }
  }
}

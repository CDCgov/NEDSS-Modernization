package gov.cdc.nbs.configuration.features.patient;

public record Patient(Add add) {
  public record Add(Add.Extended extended) {
    public record Extended(Boolean enabled) {
    }
  }
}

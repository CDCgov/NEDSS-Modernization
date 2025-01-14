package gov.cdc.nbs.configuration.features.patient;

public record Patient(Search search, Profile profile, Add add) {

  public record Search(Filters filters) {

    public record Filters(boolean enabled) {

    }

  }


  public record Profile(Boolean enabled) {
  }


  public record Add(Boolean enabled, Add.Extended extended) {
    public record Extended(Boolean enabled) {
    }
  }
}

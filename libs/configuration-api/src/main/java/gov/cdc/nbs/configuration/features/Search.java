package gov.cdc.nbs.configuration.features;

public record Search(View view) {

  public record View(boolean enabled, Table table) {
    public record Table(boolean enabled) {
    }
  }
}

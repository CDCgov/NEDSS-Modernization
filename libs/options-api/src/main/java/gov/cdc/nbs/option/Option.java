package gov.cdc.nbs.option;

public record Option(String value, String name, String label, int order) {

  public Option(String value, String name, int order) {
    this(value, name, name, order);
  }

}

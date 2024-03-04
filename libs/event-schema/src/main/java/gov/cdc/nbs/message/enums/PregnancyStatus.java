package gov.cdc.nbs.message.enums;

public enum PregnancyStatus {
  YES("Y"),
  NO("N"),
  UNKNOWN("UNK");
  private final String value;

  PregnancyStatus(final String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }
}

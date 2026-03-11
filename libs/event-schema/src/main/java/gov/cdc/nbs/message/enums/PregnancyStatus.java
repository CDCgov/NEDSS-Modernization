package gov.cdc.nbs.message.enums;

public enum PregnancyStatus {
  YES("Y"),
  NO("N"),
  UNKNOWN("UNK");

  public static PregnancyStatus resolve(final String value) {
    return value == null
        ? null
        : switch (value) {
          case "Y" -> PregnancyStatus.YES;
          case "N" -> PregnancyStatus.NO;
          case "UNK" -> PregnancyStatus.UNKNOWN;
          default -> null;
        };
  }

  private final String value;

  PregnancyStatus(final String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }
}

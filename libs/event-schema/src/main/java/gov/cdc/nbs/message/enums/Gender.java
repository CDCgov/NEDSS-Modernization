package gov.cdc.nbs.message.enums;

public enum Gender {
  F("F"),
  M("M"),
  U("U");

  public static Gender resolve(final String value) {
    return value == null
        ? null
        : switch (value.toLowerCase()) {
      case "f" -> Gender.F;
      case "m" -> Gender.M;
      case "u" -> Gender.U;
      default -> null;
    };
  }

  private final String value;

  Gender(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }
}

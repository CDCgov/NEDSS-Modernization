package gov.cdc.nbs.message.enums;

public enum Gender {
  M("M", "Male"),
  F("F", "Female"),
  U("U", "Unknown");

  public static Gender resolve(final String value) {
    return value == null
        ? null
        : switch (value.toLowerCase()) {
      case "m" -> Gender.M;
      case "f" -> Gender.F;
      case "u" -> Gender.U;
      default -> null;
    };
  }

  private final String value;
  private final String display;

  Gender(String value, String display) {
    this.value = value;
    this.display = display;
  }

  public String display() {
    return this.display;
  }

  public String value() {
    return value;
  }
}

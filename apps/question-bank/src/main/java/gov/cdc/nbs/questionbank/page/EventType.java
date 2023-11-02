package gov.cdc.nbs.questionbank.page;

public enum EventType {
  CONTACT("CON", "Contact"),
  INTERVIEW("IXS", "Interview"),
  INVESTIGATION("INV", "Investigation"),
  LAB_ISOLATE_TRACKING("ISO", "Lab Isolate Tracking"),
  LAB_REPORT("LAB", "Lab Report"),
  LAB_SUSCEPTIBILITY("SUS", "Lab Susceptibility"),
  VACCINATION("VAC", "Vaccination")
  ;

  public static EventType resolve(final String value) {
    return switch (value) {
      case "CON" -> EventType.CONTACT;
      case "IXS" -> EventType.INTERVIEW;
      case "ISO" -> EventType.LAB_ISOLATE_TRACKING;
      case "LAB" -> EventType.LAB_REPORT;
      case "SUS" -> EventType.LAB_SUSCEPTIBILITY;
      case "VAC" -> EventType.VACCINATION;
      default -> throw new IllegalStateException("Unexpected Event Type value: " + value);
    };
  }

  private final String code;
  private final String display;

  EventType(final String code, final String display) {
    this.code = code;
    this.display = display;
  }

  public String code() {
    return code;
  }

  public String display() {
    return display;
  }
}

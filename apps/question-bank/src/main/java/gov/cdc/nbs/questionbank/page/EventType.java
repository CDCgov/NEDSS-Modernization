package gov.cdc.nbs.questionbank.page;

public enum EventType {
  CONTACT("CON", "Contact"),
  INTERVIEW("IXS", "Interview"),
  INVESTIGATION("INV", "Investigation"),
  LAB_ISOLATE_TRACKING("ISO", "Lab Isolate Tracking"),
  LAB_REPORT("LAB", "Lab Report"),
  LAB_SUSCEPTIBILITY("SUS", "Lab Susceptibility"),
  VACCINATION("VAC", "Vaccination");

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

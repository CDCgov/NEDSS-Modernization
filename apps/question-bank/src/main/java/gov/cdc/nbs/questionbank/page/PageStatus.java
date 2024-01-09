package gov.cdc.nbs.questionbank.page;

public enum PageStatus {
  INITIAL_DRAFT("Initial Draft"),
  PUBLISHED_WITH_DRAFT("Published with Draft"),
  PUBLISHED("Published");

  private final String display;

  PageStatus(final String display) {
    this.display = display;
  }

  public String display() {
    return display;
  }
}

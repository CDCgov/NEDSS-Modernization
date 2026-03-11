package gov.cdc.nbs.patient.search;

/** Values that can be used to search the gender of a patient using . */
public enum SearchableGender {
  /** The Code System value for Female */
  FEMALE("F", "Female"),
  /** The Code System value for Mail */
  MALE("M", "Male"),
  /** The Code System null flavor signifier for a value that is not known. */
  UNKNOWN("U", "Unknown"),
  /**
   * A specialized search value that signifies searching for patients without a gender value
   * specified.
   */
  NO_VALUE("NO_VALUE", "No value"),
  /** Signifies that the gender value being searched with is not one of the known values. */
  UNRECOGNIZED;

  public static SearchableGender resolve(final String value) {
    return value == null
        ? null
        : switch (value.toUpperCase()) {
          case "F", "FEMALE" -> FEMALE;
          case "M", "MALE" -> MALE;
          case "U", "UNK", "UNKNOWN" -> UNKNOWN;
          case "NO_VALUE" -> NO_VALUE;
          default -> UNRECOGNIZED;
        };
  }

  private final String value;
  private final String display;

  SearchableGender() {
    this(null, null);
  }

  SearchableGender(final String value, final String display) {
    this.value = value;
    this.display = display;
  }

  public String value() {
    return value;
  }

  public String display() {
    return display;
  }
}

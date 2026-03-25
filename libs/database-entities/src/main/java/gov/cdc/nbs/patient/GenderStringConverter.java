package gov.cdc.nbs.patient;

import gov.cdc.nbs.message.enums.Gender;

public class GenderStringConverter {

  public static Gender fromString(final String value) {
    if (value == null) {
      return null;
    }
    return switch (value) {
      case "F" -> Gender.F;
      case "M" -> Gender.M;
      case "U" -> Gender.U;
      default -> null;
    };
  }

  public static String toString(final Gender value) {
    if (value == null) {
      return null;
    }
    return switch (value) {
      case F -> "F";
      case M -> "M";
      case U -> "U";
    };
  }

  private GenderStringConverter() {}
}

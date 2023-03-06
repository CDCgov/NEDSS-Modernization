package gov.cdc.nbs.patient;

import gov.cdc.nbs.message.enums.Suffix;

public class SuffixStringConverter {

  public static Suffix fromString(final String value) {
    if (value == null) {
      return null;
    }
    return switch (value) {
      case "ESQ" -> Suffix.ESQ;
      case "II" -> Suffix.II;
      case "III" -> Suffix.III;
      case "IV" -> Suffix.IV;
      case "JR" -> Suffix.JR;
      case "SR" -> Suffix.SR;
      case "V" -> Suffix.V;
      default -> null;
    };
  }

  public static String toString(final Suffix value) {
    if (value == null) {
      return null;
    }
    return switch (value) {
      case ESQ -> "ESQ";
      case II -> "II";
      case III -> "III";
      case IV -> "IV";
      case JR -> "JR";
      case SR -> "SR";
      case V -> "V";
    };
  }

  private SuffixStringConverter() {

  }
}

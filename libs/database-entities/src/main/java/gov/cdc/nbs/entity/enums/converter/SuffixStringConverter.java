package gov.cdc.nbs.entity.enums.converter;

import gov.cdc.nbs.message.enums.Suffix;

public class SuffixStringConverter {

  public static Suffix fromString(final String dbData) {
    if (dbData == null) {
      return null;
    }
    return switch (dbData) {
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

  public static String toString(final Suffix attribute) {
    if (attribute == null) {
      return null;
    }
    return switch (attribute) {
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

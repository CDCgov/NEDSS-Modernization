package gov.cdc.nbs.patient;

import gov.cdc.nbs.message.enums.Indicator;

public class IndicatorStringConverter {

  public static Indicator fromString(final String value) {
    if (value == null) {
      return null;
    }
    return switch (value) {
      case "Y" -> Indicator.YES;
      case "UNK" -> Indicator.UNKNOWN;
      case "N" -> Indicator.NO;
      default -> null;
    };
  }

  public static String toString(final Indicator value) {
    if (value == null) {
      return null;
    }
    return value.getId();
  }

  private IndicatorStringConverter() {}
}

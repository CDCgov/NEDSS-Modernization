package gov.cdc.nbs.search;

public class WildCards {

  public static String startsWith(final String value) {
    return isValid(value)
        ? escape(value.toLowerCase().trim()) + "*"
        : null;
  }

  public static String contains(final String value) {
    return isValid(value)
        ? "*" + escape(value.toLowerCase().trim()) + "*"
        : null;
  }

  public static String containsWithNamePunctuation(final String value) {
    return isValid(value)
        ? "*" + nonAlphaNumericToWilcardSeparators(value.toLowerCase().trim()) + "*"
        : null;
  }


  private static boolean isValid(final String value) {
    return value != null && !value.isEmpty();
  }

  private static String nonAlphaNumericToWilcardSeparators(final String value) {
    return value.replaceAll("[^a-zA-Z0-9]", "* *").trim();
  }

  private static String escape(final String value) {
    return value.replaceAll("([+\\-!(){}\\[\\]^\"~*?:\\\\/])", "\\\\$1");
  }

  private WildCards() {}
}

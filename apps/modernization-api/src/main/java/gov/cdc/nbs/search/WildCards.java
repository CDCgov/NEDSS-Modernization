package gov.cdc.nbs.search;

public class WildCards {

  public static String startsWith(final String value) {
    return isValid(value) ? escape(value.toLowerCase().trim()) + "*" : null;
  }

  public static String contains(final String value) {
    return isValid(value) ? "*" + wrapAlphaNumericTokensInAsteriskTokenAsterisk(value) + "*" : null;
  }

  private static boolean isValid(final String value) {
    return value != null && !value.isEmpty();
  }

  private static String wrapAlphaNumericTokensInAsteriskTokenAsterisk(final String value) {
    return String.join("* *", value.toLowerCase().trim().split("[^a-zA-Z0-9]"));
  }

  private static String escape(final String value) {
    return value.replaceAll("([+\\-!(){}\\[\\]^\"~*?:\\\\/])", "\\\\$1");
  }

  private WildCards() {}
}

package gov.cdc.nbs.search;

public class AdjustStrings {

  public static String withoutSpecialCharacters(final String value) {
    return value != null
        ? value.replaceAll("\\W", "")
        : null;
  }

  public static String withoutHyphens(final String value) {
    return value != null
        ? value.replace("-", " ")
        : null;
  }

  private AdjustStrings() {

  }

}

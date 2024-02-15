package gov.cdc.nbs.search;

public class AdjustStrings {

  public static String withoutSpecialCharacters(final String value) {
    return value != null
        ? value.replaceAll("\\W", "")
        : null;
  }

  private AdjustStrings() {

  }

}

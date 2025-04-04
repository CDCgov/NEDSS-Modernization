package gov.cdc.nbs.data;

public class LimitString {

  public static String toMaxLength(final String input, int maxLength) {
    if (input == null) {
      return null;
    }

    return input.length() > maxLength ? input.substring(0, maxLength) : input;
  }

  private LimitString() {

  }

}

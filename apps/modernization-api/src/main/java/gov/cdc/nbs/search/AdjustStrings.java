package gov.cdc.nbs.search;

import static java.util.function.Predicate.not;

import java.util.Optional;
import java.util.regex.Pattern;

public class AdjustStrings {

  private static final Pattern SPECIAL_CHARACTERS = Pattern.compile("\\W");

  public static String withoutSpecialCharacters(final String value) {
    return value != null ? SPECIAL_CHARACTERS.matcher(value).replaceAll("") : null;
  }

  public static Optional<String> maybeWithoutSpecialCharacters(final String value) {
    return Optional.ofNullable(value)
        .map(AdjustStrings::withoutSpecialCharacters)
        .filter(not(String::isBlank));
  }

  private static final Pattern ONLY_DIGITS = Pattern.compile("\\D");

  public static String onlyDigits(final String value) {
    return value == null ? null : ONLY_DIGITS.matcher(value).replaceAll("");
  }

  public static Optional<String> maybeOnlyDigits(final String value) {
    return Optional.ofNullable(value).map(AdjustStrings::onlyDigits);
  }

  public static String withoutHyphens(final String value) {
    return value != null ? value.replace("-", " ") : null;
  }

  public static Optional<String> maybeWithoutHyphens(final String value) {
    return Optional.ofNullable(value)
        .map(AdjustStrings::withoutHyphens)
        .filter(not(String::isBlank));
  }

  private AdjustStrings() {}
}

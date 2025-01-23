package gov.cdc.nbs.search.criteria.text;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.cdc.nbs.search.AdjustStrings;

import java.util.Optional;
import java.util.function.Predicate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TextCriteria(
    String equals,
    String not,
    String startsWith,
    String contains,
    String soundsLike) {

  public static TextCriteria equals(final String value) {
    return new TextCriteria(value, null, null, null, null);
  }

  public static TextCriteria not(final String value) {
    return new TextCriteria(null, value, null, null, null);
  }

  public static TextCriteria startsWith(final String value) {
    return new TextCriteria(null, null, value, null, null);
  }

  public static TextCriteria contains(final String value) {
    return new TextCriteria(null, null, null, value, null);
  }

  public static TextCriteria soundsLike(final String value) {
    return new TextCriteria(null, null, null, null, value);
  }

  public Optional<String> maybeEquals() {
    return maybeText(equals);
  }

  public Optional<String> maybeNot() {
    return maybeText(not);
  }

  public Optional<String> maybeStartsWith() {
    return maybeText(startsWith);
  }

  public Optional<String> maybeContains() {
    return maybeText(contains);
  }

  public Optional<String> maybeSoundsLike() {
    return maybeText(soundsLike);
  }

  public static Optional<String> maybeText(String value) {
    return Optional.ofNullable(value)
        .filter(Predicate.not(String::isEmpty))
        .map(AdjustStrings::withoutHyphens);
  }
}

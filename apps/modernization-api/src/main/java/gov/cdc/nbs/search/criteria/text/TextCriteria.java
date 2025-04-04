package gov.cdc.nbs.search.criteria.text;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Optional;

import static gov.cdc.nbs.search.AdjustStrings.maybeWithoutHyphens;

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
    return maybeWithoutHyphens(equals);
  }

  public Optional<String> maybeNot() {
    return maybeWithoutHyphens(not);
  }

  public Optional<String> maybeStartsWith() {
    return maybeWithoutHyphens(startsWith);
  }

  public Optional<String> maybeContains() {
    return maybeWithoutHyphens(contains);
  }

  public Optional<String> maybeSoundsLike() {
    return maybeWithoutHyphens(soundsLike);
  }

}

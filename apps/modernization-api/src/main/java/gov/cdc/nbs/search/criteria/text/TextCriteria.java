package gov.cdc.nbs.search.criteria.text;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TextCriteria(
    String equals,
    String not,
    String startsWith,
    String contains,
    String soundsLike
) {

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

}

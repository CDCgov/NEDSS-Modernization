package gov.cdc.nbs.search;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class WildCardsTest {

  @Test
  void should_add_wildcard_character_at_end_of_value() {

    String actual = WildCards.startsWith("value");

    assertThat(actual).endsWith("*");
  }

  @Test
  void should_not_add_wildcard_character_at_end_of_blank_value() {
    String actual = WildCards.startsWith("");

    assertThat(actual).isNull();
  }

  @Test
  void should_not_add_wildcard_character_at_end_of_null_value() {
    String actual = WildCards.startsWith(null);

    assertThat(actual).isNull();
  }

  @Test
  void should_add_wildcard_character_at_beginning_and__end_of_value() {

    String actual = WildCards.contains("value");

    assertThat(actual).startsWith("*").endsWith("*");
  }

  @Test
  void should_not_add_wildcard_character_at_beginning_and_end_of_blank_value() {
    String actual = WildCards.contains("");

    assertThat(actual).isNull();
  }

  @Test
  void should_not_add_wildcard_character_at_beginning_and_end_of_null_value() {
    String actual = WildCards.contains(null);

    assertThat(actual).isNull();
  }

  @Test
  void should_escape_special_characters() {
    String actual = WildCards.startsWith("value*");

    assertThat(actual).isEqualTo("value\\**");
  }
}

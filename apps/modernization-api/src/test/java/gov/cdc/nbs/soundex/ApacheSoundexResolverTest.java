package gov.cdc.nbs.soundex;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class ApacheSoundexResolverTest {

  @Test
  void should_resolve_soundex() {

    String actual = new ApacheSoundexResolver().resolve("Beste");

    assertThat(actual).isEqualTo("B230");
  }

  @ParameterizedTest
  @NullAndEmptySource
  void should_not_resolve_soundex_when_empty_or_null(final String value) {
    String actual = new ApacheSoundexResolver().resolve(value);

    assertThat(actual).isNull();
  }

  @Test
  void should_fail_resolution_when_value_contains_unmapped_characters() {
    assertThatCode(() -> new ApacheSoundexResolver().resolve("Šimková")).doesNotThrowAnyException();
  }
}

package gov.cdc.nbs.message.enums;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PregnancyStatusTest {

  @ParameterizedTest
  @MethodSource("stringToPregnancyStatus")
  void should_resolve_to_expected_value(final String in, final PregnancyStatus expected) {

    PregnancyStatus resolved = PregnancyStatus.resolve(in);

    assertThat(resolved).isEqualTo(expected);
  }

  static Stream<Arguments> stringToPregnancyStatus() {
    return Stream.of(
        arguments("Y", PregnancyStatus.YES),
        arguments("N", PregnancyStatus.NO),
        arguments("UNK", PregnancyStatus.UNKNOWN));
  }

  @Test
  void should_resolve_unexpected_value_to_null() {
    PregnancyStatus resolved = PregnancyStatus.resolve("unexpected");

    assertThat(resolved).isNull();
  }

  @Test
  void should_resolve_null_to_null() {
    PregnancyStatus resolved = PregnancyStatus.resolve(null);

    assertThat(resolved).isNull();
  }
}

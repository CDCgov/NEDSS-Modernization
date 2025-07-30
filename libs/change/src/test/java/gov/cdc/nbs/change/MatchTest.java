package gov.cdc.nbs.change;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MatchTest {

  @Test
  void should_require_left_or_right_value() {

    assertThatThrownBy(() -> Match.of(null, null))
        .isInstanceOf(IllegalStateException.class);

  }

  @Test
  void should_create_both_match() {
    var actual = Match.of("left", "right");

    assertThat(actual).isInstanceOf(Match.Both.class);
  }

  @Test
  void should_create_left_only_match() {
    var actual = Match.of("left", null);

    assertThat(actual).isInstanceOf(Match.OnlyLeft.class);
  }

  @Test
  void should_create_right_only_match() {
    var actual = Match.of(null, "right");

    assertThat(actual).isInstanceOf(Match.OnlyRight.class);
  }
}

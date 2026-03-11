package gov.cdc.nbs.change;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cdc.nbs.change.Match.Both;
import gov.cdc.nbs.change.Match.OnlyLeft;
import gov.cdc.nbs.change.Match.OnlyRight;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class MatchResolverTest {

  @Test
  void should_return_only_right_matches_when_left_is_empty() {
    // setup
    List<String> original = List.of();

    List<String> incoming = List.of("one", "two", "three", "four", "five", "six", "seven");

    //  execute
    MatchResolver<String, String, String> resolver =
        new MatchResolver<>(Function.identity(), Function.identity());

    Stream<Match<String, String>> actual = resolver.resolve(original, incoming);

    //  verify
    assertThat(actual)
        .satisfiesExactlyInAnyOrder(
            match -> assertThat(match).isEqualTo(new OnlyRight<>("one")),
            match -> assertThat(match).isEqualTo(new OnlyRight<>("two")),
            match -> assertThat(match).isEqualTo(new OnlyRight<>("three")),
            match -> assertThat(match).isEqualTo(new OnlyRight<>("four")),
            match -> assertThat(match).isEqualTo(new OnlyRight<>("five")),
            match -> assertThat(match).isEqualTo(new OnlyRight<>("six")),
            match -> assertThat(match).isEqualTo(new OnlyRight<>("seven")));
  }

  @Test
  void should_return_only_left_matches_when_right_is_empty() {
    // setup
    List<String> original = List.of("one", "two", "three", "four", "five", "six", "seven");

    List<String> incoming = List.of();

    //  execute
    MatchResolver<String, String, String> resolver =
        new MatchResolver<>(Function.identity(), Function.identity());

    Stream<Match<String, String>> actual = resolver.resolve(original, incoming);

    //  verify
    assertThat(actual)
        .satisfiesExactlyInAnyOrder(
            match -> assertThat(match).isEqualTo(new OnlyLeft<>("one")),
            match -> assertThat(match).isEqualTo(new OnlyLeft<>("two")),
            match -> assertThat(match).isEqualTo(new OnlyLeft<>("three")),
            match -> assertThat(match).isEqualTo(new OnlyLeft<>("four")),
            match -> assertThat(match).isEqualTo(new OnlyLeft<>("five")),
            match -> assertThat(match).isEqualTo(new OnlyLeft<>("six")),
            match -> assertThat(match).isEqualTo(new OnlyLeft<>("seven")));
  }

  @Test
  void
      should_return_only_left_and_only_right_matches_when_left_and_right_do_not_contain_equal_elements() {
    // setup
    List<String> original = List.of("one", "three", "five", "seven");

    List<String> incoming = List.of("two", "four", "six");

    //  execute
    MatchResolver<String, String, String> resolver =
        new MatchResolver<>(Function.identity(), Function.identity());

    Stream<Match<String, String>> actual = resolver.resolve(original, incoming);

    //  verify
    assertThat(actual)
        .satisfiesExactlyInAnyOrder(
            match -> assertThat(match).isEqualTo(new OnlyLeft<>("one")),
            match -> assertThat(match).isEqualTo(new OnlyRight<>("two")),
            match -> assertThat(match).isEqualTo(new OnlyLeft<>("three")),
            match -> assertThat(match).isEqualTo(new OnlyRight<>("four")),
            match -> assertThat(match).isEqualTo(new OnlyLeft<>("five")),
            match -> assertThat(match).isEqualTo(new OnlyRight<>("six")),
            match -> assertThat(match).isEqualTo(new OnlyLeft<>("seven")));
  }

  @Test
  void should_return_only_left_matches_when_right_does_not_contain_the_values_of_left() {
    // setup
    List<String> original = List.of("one", "two", "three", "four", "five", "six", "seven");

    List<String> incoming = List.of("one", "three", "five", "seven");

    //  execute
    MatchResolver<String, String, String> resolver =
        new MatchResolver<>(Function.identity(), Function.identity());

    Stream<Match<String, String>> actual = resolver.resolve(original, incoming);

    //  verify
    assertThat(actual)
        .satisfiesExactlyInAnyOrder(
            match -> assertThat(match).isEqualTo(new Both<>("one", "one")),
            match -> assertThat(match).isEqualTo(new OnlyLeft<>("two")),
            match -> assertThat(match).isEqualTo(new Both<>("three", "three")),
            match -> assertThat(match).isEqualTo(new OnlyLeft<>("four")),
            match -> assertThat(match).isEqualTo(new Both<>("five", "five")),
            match -> assertThat(match).isEqualTo(new OnlyLeft<>("six")),
            match -> assertThat(match).isEqualTo(new Both<>("seven", "seven")));
  }

  @Test
  void should_return_only_right_matches_when_left_does_not_contain_the_values_of_right() {
    // setup
    List<String> original = List.of("one", "three", "five", "seven");

    List<String> incoming = List.of("one", "two", "three", "four", "five", "six", "seven");

    //  execute
    MatchResolver<String, String, String> resolver =
        new MatchResolver<>(Function.identity(), Function.identity());

    Stream<Match<String, String>> actual = resolver.resolve(original, incoming);

    //  verify
    assertThat(actual)
        .satisfiesExactlyInAnyOrder(
            match -> assertThat(match).isEqualTo(new Both<>("one", "one")),
            match -> assertThat(match).isEqualTo(new OnlyRight<>("two")),
            match -> assertThat(match).isEqualTo(new Both<>("three", "three")),
            match -> assertThat(match).isEqualTo(new OnlyRight<>("four")),
            match -> assertThat(match).isEqualTo(new Both<>("five", "five")),
            match -> assertThat(match).isEqualTo(new OnlyRight<>("six")),
            match -> assertThat(match).isEqualTo(new Both<>("seven", "seven")));
  }

  @Test
  void should_return_both_matches_when_left_contains_the_same_values_of_right_in_any() {
    // setup
    List<String> original = List.of("one", "two", "three", "four", "five", "six", "seven");

    List<String> incoming = List.of("one", "two", "three", "four", "five", "six", "seven");

    //  execute
    MatchResolver<String, String, String> resolver =
        new MatchResolver<>(Function.identity(), Function.identity());

    Stream<Match<String, String>> actual = resolver.resolve(original, incoming);

    //  verify
    assertThat(actual)
        .satisfiesExactlyInAnyOrder(
            match -> assertThat(match).isEqualTo(new Both<>("one", "one")),
            match -> assertThat(match).isEqualTo(new Both<>("two", "two")),
            match -> assertThat(match).isEqualTo(new Both<>("three", "three")),
            match -> assertThat(match).isEqualTo(new Both<>("four", "four")),
            match -> assertThat(match).isEqualTo(new Both<>("five", "five")),
            match -> assertThat(match).isEqualTo(new Both<>("six", "six")),
            match -> assertThat(match).isEqualTo(new Both<>("seven", "seven")));
  }

  @Test
  void should_return_both_matches_when_left_contains_the_same_values_of_right_in_any_order() {
    // setup
    List<String> original = List.of("seven", "two", "six", "three", "five", "one", "four");

    List<String> incoming = List.of("one", "two", "three", "four", "five", "six", "seven");

    //  execute
    MatchResolver<String, String, String> resolver =
        new MatchResolver<>(Function.identity(), Function.identity());

    Stream<Match<String, String>> actual = resolver.resolve(original, incoming);

    //  verify
    assertThat(actual)
        .satisfiesExactlyInAnyOrder(
            match -> assertThat(match).isEqualTo(new Both<>("one", "one")),
            match -> assertThat(match).isEqualTo(new Both<>("two", "two")),
            match -> assertThat(match).isEqualTo(new Both<>("three", "three")),
            match -> assertThat(match).isEqualTo(new Both<>("four", "four")),
            match -> assertThat(match).isEqualTo(new Both<>("five", "five")),
            match -> assertThat(match).isEqualTo(new Both<>("six", "six")),
            match -> assertThat(match).isEqualTo(new Both<>("seven", "seven")));
  }
}

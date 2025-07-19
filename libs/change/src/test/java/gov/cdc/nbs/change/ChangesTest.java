package gov.cdc.nbs.change;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ChangesTest {

  @Test
  void should_resolve_additions_from_only_right_matches() {
    List<Match<String, String>> matches = List.of(
        new Match.OnlyRight<>("one"),
        new Match.Both<>("two", "two"),
        new Match.OnlyLeft<>("three"),
        new Match.OnlyRight<>("nine")
    );

    Changes<String, String> changes = new Changes<>(matches);

    Stream<String> actual = changes.added();

    assertThat(actual).containsExactly("one", "nine");
  }

  @Test
  void should_not_resolve_additions_without_only_right_matches() {
    List<Match<String, String>> matches = List.of(
        new Match.Both<>("two", "two"),
        new Match.OnlyLeft<>("three")
    );

    Changes<String, String> changes = new Changes<>(matches);

    Stream<String> actual = changes.added();

    assertThat(actual).isEmpty();
  }

  @Test
  void should_resolve_default_alteration_based_on_non_equal_both_matches() {
    List<Match<String, String>> matches = List.of(
        new Match.OnlyRight<>("one"),
        new Match.Both<>("two", "two"),
        new Match.Both<>("X", "Y"),
        new Match.OnlyLeft<>("three")
    );

    Changes<String, String> changes = new Changes<>(matches);

    Stream<Match.Both<String, String>> actual = changes.altered();

    assertThat(actual).satisfiesExactly(
        actual_match -> assertAll(
            () -> assertThat(actual_match.left()).isEqualTo("X"),
            () -> assertThat(actual_match.right()).isEqualTo("Y")
        )
    );
  }


  @Test
  void should_not_resolve_default_alteration_without_both_matches() {
    List<Match<String, String>> matches = List.of(
        new Match.OnlyRight<>("one"),
        new Match.OnlyLeft<>("three")
    );

    Changes<String, String> changes = new Changes<>(matches);

    Stream<Match.Both<String, String>> actual = changes.altered();

    assertThat(actual).isEmpty();
  }


  @Test
  void should_resolve_alteration_based_on_non_equal_both_matches() {
    List<Match<String, String>> matches = List.of(
        new Match.OnlyRight<>("one"),
        new Match.Both<>("TWO", "two"),
        new Match.Both<>("X", "Y"),
        new Match.OnlyLeft<>("three")
    );

    Changes<String, String> changes = new Changes<>(matches);

    Stream<Match.Both<String, String>> actual = changes.altered((l, r) -> !l.equalsIgnoreCase(r));

    assertThat(actual).satisfiesExactly(
        actual_match -> assertAll(
            () -> assertThat(actual_match.left()).isEqualTo("X"),
            () -> assertThat(actual_match.right()).isEqualTo("Y")
        )
    );
  }


  @Test
  void should_not_resolve_alteration_without_both_matches() {
    List<Match<String, String>> matches = List.of(
        new Match.OnlyRight<>("one"),
        new Match.OnlyLeft<>("three")
    );

    Changes<String, String> changes = new Changes<>(matches);

    Stream<Match.Both<String, String>> actual = changes.altered((l, r) -> !l.equalsIgnoreCase(r));

    assertThat(actual).isEmpty();
  }

  @Test
  void should_not_resolve_alteration_without_qualifying_matches() {
    List<Match<String, String>> matches = List.of(
        new Match.OnlyRight<>("one"),
        new Match.OnlyLeft<>("three"),
        new Match.Both<>("TWO", "two")
    );

    Changes<String, String> changes = new Changes<>(matches);

    Stream<Match.Both<String, String>> actual = changes.altered((l, r) -> !l.equalsIgnoreCase(r));

    assertThat(actual).isEmpty();
  }


  @Test
  void should_resolve_removal_from_only_right_matches() {
    List<Match<String, String>> matches = List.of(
        new Match.OnlyRight<>("one"),
        new Match.Both<>("two", "two"),
        new Match.OnlyLeft<>("three"),
        new Match.OnlyRight<>("nine")
    );

    Changes<String, String> changes = new Changes<>(matches);

    Stream<String> actual = changes.added();

    assertThat(actual).containsExactly("one", "nine");
  }

  @Test
  void should_not_resolve_removal_without_only_right_matches() {
    List<Match<String, String>> matches = List.of(
        new Match.Both<>("two", "two"),
        new Match.OnlyLeft<>("three")
    );

    Changes<String, String> changes = new Changes<>(matches);

    Stream<String> actual = changes.added();

    assertThat(actual).isEmpty();
  }

}

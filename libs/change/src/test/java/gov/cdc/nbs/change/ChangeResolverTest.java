package gov.cdc.nbs.change;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ChangeResolverTest {


  @Test
  void should_return_added_matches_for_same_type() {
    // setup
    List<String> original = List.of();

    List<String> incoming = List.of("one", "two", "three", "four", "five", "six", "seven");

    //  execute
    Changes<String, String> changes = ChangeResolver.<String>simple()
        .resolve(original, incoming);

    //  verify
    assertThat(changes.added()).satisfiesExactlyInAnyOrder(
        actual -> assertThat(actual).isEqualTo("one"),
        actual -> assertThat(actual).isEqualTo("two"),
        actual -> assertThat(actual).isEqualTo("three"),
        actual -> assertThat(actual).isEqualTo("four"),
        actual -> assertThat(actual).isEqualTo("five"),
        actual -> assertThat(actual).isEqualTo("six"),
        actual -> assertThat(actual).isEqualTo("seven")
    );

    assertThat(changes.removed()).isEmpty();
    assertThat(changes.altered()).isEmpty();
  }

  @Test
  void should_return_added_matches_not_present_in_original_list_for_same_type() {
    // setup
    List<String> original = List.of("one", "two", "three");

    List<String> incoming = List.of("one", "two", "three", "four", "five", "six", "seven");

    //  execute
    Changes<String, String> changes = ChangeResolver.<String, String>ofSameType(Function.identity())
        .resolve(original, incoming);

    //  verify
    assertThat(changes.added()).satisfiesExactlyInAnyOrder(
        actual -> assertThat(actual).isEqualTo("four"),
        actual -> assertThat(actual).isEqualTo("five"),
        actual -> assertThat(actual).isEqualTo("six"),
        actual -> assertThat(actual).isEqualTo("seven")
    );

    assertThat(changes.removed()).isEmpty();
    assertThat(changes.altered()).isEmpty();
  }

  @Test
  void should_return_removed_matches_for_same_type() {
    // setup
    List<String> original = List.of("one", "two", "three", "four", "five", "six", "seven");

    List<String> incoming = List.of();

    //  execute
    Changes<String, String> changes = ChangeResolver.<String, String>ofSameType(Function.identity())
        .resolve(original, incoming);

    //  verify
    assertThat(changes.added()).isEmpty();

    assertThat(changes.removed()).satisfiesExactlyInAnyOrder(
        actual -> assertThat(actual).isEqualTo("one"),
        actual -> assertThat(actual).isEqualTo("two"),
        actual -> assertThat(actual).isEqualTo("three"),
        actual -> assertThat(actual).isEqualTo("four"),
        actual -> assertThat(actual).isEqualTo("five"),
        actual -> assertThat(actual).isEqualTo("six"),
        actual -> assertThat(actual).isEqualTo("seven")
    );

    assertThat(changes.altered()).isEmpty();
  }

  @Test
  void should_return_removed_matches_not_present_in_incoming_list_for_same_type() {
    // setup
    List<String> original = List.of("one", "two", "three", "four", "five", "six", "seven");

    List<String> incoming = List.of("one", "three", "five", "seven");

    //  execute
    Changes<String, String> changes = ChangeResolver.<String, String>ofSameType(Function.identity())
        .resolve(original, incoming);

    //  verify
    assertThat(changes.added()).isEmpty();

    assertThat(changes.removed()).satisfiesExactlyInAnyOrder(
        actual -> assertThat(actual).isEqualTo("two"),
        actual -> assertThat(actual).isEqualTo("four"),
        actual -> assertThat(actual).isEqualTo("six")
    );

    assertThat(changes.altered()).isEmpty();
  }


  @Test
  void should_return_changed_matches_for_same_type_using_default_change_decider() {
    //  setup

    List<Tuple<Integer, String>> original =
        List.of(new Tuple<>(1, "one"), new Tuple<>(2, "two"), new Tuple<>(3, "three"), new Tuple<>(4, "four"));

    List<Tuple<Integer, String>> incoming =
        List.of(new Tuple<>(1, "one"), new Tuple<>(2, "too"), new Tuple<>(3, "three"), new Tuple<>(4, "for"));

    //  execute
    Changes<Tuple<Integer, String>, Tuple<Integer, String>> changes =
        ChangeResolver.<Tuple<Integer, String>, Integer>ofSameType(Tuple::x)
            .resolve(original, incoming);


    //  verify
    assertThat(changes.added()).isEmpty();
    assertThat(changes.removed()).isEmpty();
    assertThat(changes.altered()).satisfiesExactlyInAnyOrder(
        actual -> assertAll(
            () -> assertThat(actual.left().x()).isEqualTo(2),
            () -> assertThat(actual.left().y()).isEqualTo("two"),
            () -> assertThat(actual.right().x()).isEqualTo(2),
            () -> assertThat(actual.right().y()).isEqualTo("too")
        ),
        actual -> assertAll(
            () -> assertThat(actual.left().x()).isEqualTo(4),
            () -> assertThat(actual.left().y()).isEqualTo("four"),
            () -> assertThat(actual.right().x()).isEqualTo(4),
            () -> assertThat(actual.right().y()).isEqualTo("for")
        )
    );
  }

  @Test
  void should_return_changed_matches_for_same_type_using_provided_change_decider() {
    //  setup

    List<Tuple<Integer, String>> original =
        List.of(new Tuple<>(1, "one"), new Tuple<>(2, "two"), new Tuple<>(3, "three"), new Tuple<>(4, "four"));

    List<Tuple<Integer, String>> incoming =
        List.of(new Tuple<>(1, "one"), new Tuple<>(2, "too"), new Tuple<>(3, "three"), new Tuple<>(4, "for"));

    //  execute
    Changes<Tuple<Integer, String>, Tuple<Integer, String>> changes =
        ChangeResolver.<Tuple<Integer, String>, Integer>ofSameType(Tuple::x)
            .resolve(original, incoming);

    BiPredicate<Tuple<Integer, String>, Tuple<Integer, String>> condition =
        (left, right) -> !Objects.equals(right.y(), left.y());

    Stream<Match.Both<Tuple<Integer, String>, Tuple<Integer, String>>> actual = changes.altered(condition);

    //  verify
    assertThat(changes.added()).isEmpty();
    assertThat(changes.removed()).isEmpty();

    assertThat(actual)
        .satisfiesExactlyInAnyOrder(
            match -> assertAll(
                () -> assertThat(match.left().x()).isEqualTo(2),
                () -> assertThat(match.left().y()).isEqualTo("two"),
                () -> assertThat(match.right().x()).isEqualTo(2),
                () -> assertThat(match.right().y()).isEqualTo("too")
            ),
            match -> assertAll(
                () -> assertThat(match.left().x()).isEqualTo(4),
                () -> assertThat(match.left().y()).isEqualTo("four"),
                () -> assertThat(match.right().x()).isEqualTo(4),
                () -> assertThat(match.right().y()).isEqualTo("for")
            )
        );

  }

  @Test
  void should_return_changed_matches_for_differing_types() {
    //  setup

    List<Tuple<Integer, Integer>> original =
        List.of(new Tuple<>(1, 1), new Tuple<>(2, 2), new Tuple<>(3, 3), new Tuple<>(4, 4));

    List<Tuple<Integer, String>> incoming =
        List.of(new Tuple<>(1, "1"), new Tuple<>(2, "two"), new Tuple<>(3, "3"), new Tuple<>(4, "four"));

    //  execute
    Changes<Tuple<Integer, Integer>, Tuple<Integer, String>> changes =
        ChangeResolver.<Tuple<Integer, Integer>, Tuple<Integer, String>, Integer>ofDifferingTypes(Tuple::x, Tuple::x)
            .resolve(original, incoming);

    BiPredicate<Tuple<Integer, Integer>, Tuple<Integer, String>> condition =
        (left, right) -> !Objects.equals(right.y(), String.valueOf(left.y()));

    Stream<Match.Both<Tuple<Integer, Integer>, Tuple<Integer, String>>> actual = changes.altered(condition);

    //  verify
    assertThat(changes.added()).isEmpty();
    assertThat(changes.removed()).isEmpty();

    assertThat(actual)
        .satisfiesExactlyInAnyOrder(
            match -> assertAll(
                () -> assertThat(match.left().x()).isEqualTo(2),
                () -> assertThat(match.left().y()).isEqualTo(2),
                () -> assertThat(match.right().x()).isEqualTo(2),
                () -> assertThat(match.right().y()).isEqualTo("two")
            ),
            match -> assertAll(
                () -> assertThat(match.left().x()).isEqualTo(4),
                () -> assertThat(match.left().y()).isEqualTo(4),
                () -> assertThat(match.right().x()).isEqualTo(4),
                () -> assertThat(match.right().y()).isEqualTo("four")
            )
        );
  }

}

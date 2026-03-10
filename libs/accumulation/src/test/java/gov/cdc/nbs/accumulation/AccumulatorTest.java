package gov.cdc.nbs.accumulation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class AccumulatorTest {

  record Thing(long identifier, Collection<String> value) {

    Thing merge(final Thing other) {

      ArrayList<String> merged = new ArrayList<>(this.value);
      merged.addAll(other.value);

      return new Thing(identifier, merged);
    }
  }

  @Test
  void should_collect_instances_with_same_id_into_one_instance() {

    List<Thing> accumulated =
        Stream.of(
                new Thing(
                    71L,
                    List.of(
                        "846a080f-fcd4-4d73-a91d-1e0668012045",
                        "36ac9b31-9d86-46c7-bf95-6e2b3dd86b61")),
                new Thing(71L, List.of("a9cd5557-97f2-479d-8838-c333adf6005e")))
            .collect(Accumulator.collecting(Thing::identifier, Thing::merge));

    assertThat(accumulated)
        .satisfiesExactly(
            actual ->
                assertAll(
                    () -> assertThat(actual.identifier()).isEqualTo(71),
                    () ->
                        assertThat(actual.value())
                            .contains(
                                "846a080f-fcd4-4d73-a91d-1e0668012045",
                                "36ac9b31-9d86-46c7-bf95-6e2b3dd86b61",
                                "a9cd5557-97f2-479d-8838-c333adf6005e")));
  }

  @Test
  void should_collect_instances_with_same_id_into_one_instance_by_id_in_parallel() {

    List<Thing> accumulated =
        Stream.of(
                new Thing(
                    71L,
                    List.of(
                        "846a080f-fcd4-4d73-a91d-1e0668012045",
                        "36ac9b31-9d86-46c7-bf95-6e2b3dd86b61")),
                new Thing(71L, List.of("a9cd5557-97f2-479d-8838-c333adf6005e")))
            .parallel()
            .collect(Accumulator.collecting(Thing::identifier, Thing::merge));

    assertThat(accumulated)
        .satisfiesExactly(
            actual ->
                assertAll(
                    () -> assertThat(actual.identifier()).isEqualTo(71),
                    () ->
                        assertThat(actual.value())
                            .contains(
                                "846a080f-fcd4-4d73-a91d-1e0668012045",
                                "36ac9b31-9d86-46c7-bf95-6e2b3dd86b61",
                                "a9cd5557-97f2-479d-8838-c333adf6005e")));
  }

  @Test
  void should_collect_instances_with_differing_id_into_multiple_instances_by_id() {

    List<Thing> accumulated =
        Stream.of(
                new Thing(
                    71L,
                    List.of(
                        "846a080f-fcd4-4d73-a91d-1e0668012045",
                        "36ac9b31-9d86-46c7-bf95-6e2b3dd86b61")),
                new Thing(281, List.of("a9cd5557-97f2-479d-8838-c333adf6005e")))
            .collect(Accumulator.collecting(Thing::identifier, Thing::merge));

    assertThat(accumulated)
        .satisfiesExactlyInAnyOrder(
            actual ->
                assertAll(
                    () -> assertThat(actual.identifier()).isEqualTo(71L),
                    () ->
                        assertThat(actual.value())
                            .contains(
                                "846a080f-fcd4-4d73-a91d-1e0668012045",
                                "36ac9b31-9d86-46c7-bf95-6e2b3dd86b61")),
            actual ->
                assertAll(
                    () -> assertThat(actual.identifier()).isEqualTo(281L),
                    () ->
                        assertThat(actual.value())
                            .contains("a9cd5557-97f2-479d-8838-c333adf6005e")));
  }

  @Test
  void should_accumulate_instances_with_same_id_into_one_instance_by_id() {

    Optional<Thing> accumulated =
        Stream.of(
                new Thing(
                    71L,
                    List.of(
                        "846a080f-fcd4-4d73-a91d-1e0668012045",
                        "36ac9b31-9d86-46c7-bf95-6e2b3dd86b61")),
                new Thing(71L, List.of("a9cd5557-97f2-479d-8838-c333adf6005e")))
            .collect(Accumulator.accumulating(Thing::identifier, Thing::merge));

    assertThat(accumulated)
        .hasValueSatisfying(
            actual ->
                assertAll(
                    () -> assertThat(actual.identifier()).isEqualTo(71),
                    () ->
                        assertThat(actual.value())
                            .contains(
                                "846a080f-fcd4-4d73-a91d-1e0668012045",
                                "36ac9b31-9d86-46c7-bf95-6e2b3dd86b61",
                                "a9cd5557-97f2-479d-8838-c333adf6005e")));
  }

  @Test
  void should_not_be_present_when_accumulating_zero_instances_by_id() {

    Optional<Thing> accumulated =
        Stream.<Thing>empty().collect(Accumulator.accumulating(Thing::identifier, Thing::merge));

    assertThat(accumulated).isNotPresent();
  }

  @Test
  void should_accumulate_multiple_instances_into_one() {

    Optional<String> accumulated =
        Stream.of("O", "N", "E").collect(Accumulator.accumulating(String::concat));

    assertThat(accumulated).contains("ONE");
  }

  @Test
  void should_accumulate_multiple_instances_into_one_in_parallel() {

    Optional<String> accumulated =
        Stream.of("P", "A", "R", "A", "L", "L", "E", "L")
            .parallel()
            .collect(Accumulator.accumulating(String::concat));

    assertThat(accumulated).contains("PARALLEL");
  }

  @Test
  void should_not_be_present_when_accumulating_zero_instances() {
    Optional<String> accumulated =
        Stream.<String>empty().collect(Accumulator.accumulating(String::concat));

    assertThat(accumulated).isNotPresent();
  }
}

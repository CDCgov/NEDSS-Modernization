package gov.cdc.nbs.testing.support;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AvailableTest {

  @Test
  void should_throw_an_exception_when_nothing_is_available() {
    Available<Object> available = new Available<>();

    assertThatThrownBy(available::one)
        .hasMessageContaining("there are none available");

  }

  @Test
  void should_return_the_only_available_item() {

    Available<Object> available = new Available<>();

    Object item = new Object();

    available.available(item);

    assertThat(available.one()).isSameAs(item);

  }

  @Test
  void should_return_the_first_available_item() {

    Available<Object> available = new Available<>();

    Object item = new Object();

    available.available(item);
    available.available(new Object());

    assertThat(available.one()).isSameAs(item);

  }

  @Test
  void should_return_the_first_available_item_after_being_selected() {

    Available<Object> available = new Available<>();

    Object one = new Object();
    Object two = new Object();
    Object three = new Object();

    available.available(one);
    available.available(two);
    available.available(three);

    available.selectFirst(item -> item == two);

    assertThat(available.one()).isSameAs(two);

  }

  @Test
  void should_replace_the_first_available() {

    Available<Object> available = new Available<>();

    Object one = new Object();
    Object two = new Object();
    Object three = new Object();
    Object changed = new Object();

    available.available(one);
    available.available(two);
    available.available(three);

    available.first(current -> changed);

    assertThat(available.one()).isSameAs(changed);

  }

  @Test
  void should_throw_an_exception_when_reset() {
    Available<Object> available = new Available<>();

    Object item = new Object();

    available.available(item);

    available.reset();

    assertThatThrownBy(available::one)
        .hasMessageContaining("there are none available");

  }

  @Test
  void should_contain_the_only_item() {
    Available<Object> available = new Available<>();

    Object item = new Object();

    available.available(item);

    assertThat(available.maybeOne()).containsSame(item);

  }

  @Test
  void should_contain_the_first_item() {
    Available<Object> available = new Available<>();

    Object item = new Object();

    available.available(item);
    available.available(new Object());

    assertThat(available.maybeOne()).containsSame(item);

  }

  @Test
  void should_not_be_present_when_nothing_is_available() {
    Available<Object> available = new Available<>();

    assertThat(available.maybeOne()).isNotPresent();

  }

  @Test
  void should_empty_when_nothing_is_available() {
    Available<Object> available = new Available<>();

    assertThat(available.all()).isEmpty();

  }

  @Test
  void should_return_the_available_items() {

    Available<Object> available = new Available<>();

    Object item = new Object();

    available.available(item);

    assertThat(available.all()).contains(item);

  }

  @Test
  void should_empty_when_reset() {
    Available<Object> available = new Available<>();

    Object item = new Object();

    available.available(item);

    available.reset();

    assertThat(available.all()).isEmpty();

  }

  @Test
  void should_be_empty_when_indexing_with_nothing_available() {
    Available<Object> available = new Available<>();

    assertThat(available.indexed()).isEmpty();
  }

  @Test
  void should_be_empty_when_indexing_reset_available() {
    Available<Object> available = new Available<>();

    Object item = new Object();

    available.available(item);
    available.available(new Object());
    available.reset();

    assertThat(available.indexed()).isEmpty();

  }

  @Test
  void should_be_indexed_based_on_insertion_available() {
    Available<Object> available = new Available<>();

    Object one = new Object();
    Object two = new Object();
    Object three = new Object();

    available.available(one);
    available.available(two);
    available.available(three);

    assertThat(available.indexed())
        .satisfiesExactly(
          indexed -> assertThat(indexed)
              .satisfies(
                  index -> assertThat(index.index()).isZero()
              )
              .satisfies(
                  index -> assertThat(index.item()).isSameAs(one)
              ),
            indexed -> assertThat(indexed)
                .satisfies(
                    index -> assertThat(index.index()).isEqualTo(1)
                )
                .satisfies(
                    index -> assertThat(index.item()).isSameAs(two)
                ),
            indexed -> assertThat(indexed)
                .satisfies(
                    index -> assertThat(index.index()).isEqualTo(2)
                )
                .satisfies(
                    index -> assertThat(index.item()).isSameAs(three)
                )
        );

  }

}

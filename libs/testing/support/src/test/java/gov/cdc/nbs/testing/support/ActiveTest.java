package gov.cdc.nbs.testing.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ActiveTest {

  @Test
  void should_throw_an_exception_when_nothing_is_active() {
    Active<Object> active = new Active<>();

    assertThatThrownBy(active::active).hasMessageContaining("there is nothing active");
  }

  @Test
  void should_not_be_present_when_nothing_is_active() {
    Active<Object> active = new Active<>();

    assertThat(active.maybeActive()).isNotPresent();
  }

  @Test
  void should_return_the_active_item() {

    Active<Object> active = new Active<>();

    Object item = new Object();

    active.active(item);

    assertThat(active.active()).isSameAs(item);
  }

  @Test
  void should_the_active_item_should_be_present() {

    Active<Object> active = new Active<>();

    Object item = new Object();

    active.active(item);

    assertThat(active.maybeActive()).containsSame(item);
  }

  @Test
  void should_return_the_newest_active_item() {

    Active<Object> active = new Active<>();

    active.active(new Object());

    Object item = new Object();

    active.active(item);

    assertThat(active.active()).isSameAs(item);
  }

  @Test
  void should_the_newest_active_item_should_be_present() {

    Active<Object> active = new Active<>();

    active.active(new Object());

    Object item = new Object();

    active.active(item);

    assertThat(active.maybeActive()).containsSame(item);
  }

  @Test
  void should_throw_an_exception_when_reset() {

    Active<Object> active = new Active<>();

    active.active(new Object());
    active.reset();

    assertThatThrownBy(active::active).hasMessageContaining("there is nothing active");
  }

  @Test
  void should_not_be_present_when_reset() {

    Active<Object> active = new Active<>();

    active.active(new Object());
    active.reset();

    assertThat(active.maybeActive()).isNotPresent();
  }
}

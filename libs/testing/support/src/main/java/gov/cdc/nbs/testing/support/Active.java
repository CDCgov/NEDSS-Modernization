package gov.cdc.nbs.testing.support;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class Active<I> {

  private final Supplier<I> initializer;
  private I item;

  public Active(final Supplier<I> initializer) {
    this.initializer = initializer;
  }

  public Active() {
    this(() -> null);
  }

  public Active(final I initial) {
    this(() -> initial);
  }

  public void active(final I next) {
    this.item = next;
  }

  public void active(final UnaryOperator<I> next, final Supplier<I> initializer) {
    I existing = existing();

    if (existing == null) {
      existing = initializer.get();
    }

    this.item = next.apply(existing);
  }

  public void active(final UnaryOperator<I> next) {
    I existing = existing();
    if (existing != null) {
      this.item = next.apply(this.item);
    }
  }

  private I existing() {
    if (this.item == null) {
      this.item = this.initializer.get();
    }

    return this.item;
  }

  public void reset() {
    this.item = null;
  }

  public Optional<I> maybeActive() {
    return Optional.ofNullable(existing());
  }

  public I active() {
    return maybeActive().orElseThrow(() -> new IllegalStateException("there is nothing active"));
  }
}

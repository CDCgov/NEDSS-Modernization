package gov.cdc.nbs.testing.support;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@Component
public class Active<I> {

  private I item;

  public void active(final I next) {
    this.item = next;
  }

  public void active(final UnaryOperator<I> next, final Supplier<I> initializer) {
    if (this.item != null) {
      this.item = initializer.get();
    }
    this.item = next.apply(this.item);
  }

  public void active(final UnaryOperator<I> next) {
    if (this.item != null) {
      this.item = next.apply(this.item);
    }
  }

  public void reset() {
    this.item = null;
  }

  public Optional<I> maybeActive() {
    return Optional.ofNullable(this.item);
  }

  public I active() {
    return maybeActive()
        .orElseThrow(() -> new IllegalStateException("there is nothing active"));
  }

}

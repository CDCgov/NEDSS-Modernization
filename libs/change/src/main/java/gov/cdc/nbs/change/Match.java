package gov.cdc.nbs.change;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public sealed interface Match<L, R> {

  static <L, R> Match<L, R> of(L left, R right) {
    if (left != null && right != null) {
      return new Both<>(left, right);
    } else if (left == null && right != null) {
      return new OnlyRight<>(right);
    } else if (left != null) {
      return new OnlyLeft<>(left);
    } else {
      throw new IllegalStateException("Either a left or a right value is required.");
    }
  }

  record OnlyRight<L, R>(R right) implements Match<L, R> {

    public void usingRight(final Consumer<R> consumer) {
      Objects.requireNonNull(consumer, "A consumer is required.")
          .accept(right);
    }
  }


  record OnlyLeft<L, R>(L left) implements Match<L, R> {

    public void usingLeft(final Consumer<L> consumer) {
      Objects.requireNonNull(consumer, "A consumer is required.")
          .accept(left);
    }
  }


  record Both<L, R>(L left, R right) implements Match<L, R> {

    public void usingBoth(final BiConsumer<L, R> consumer) {
      Objects.requireNonNull(consumer, "A consumer for left and right is required.")
          .accept(left, right);
    }

    public <T> T withBoth(final BiFunction<L, R, T> function) {
      return function.apply(left(), right());
    }
  }

}

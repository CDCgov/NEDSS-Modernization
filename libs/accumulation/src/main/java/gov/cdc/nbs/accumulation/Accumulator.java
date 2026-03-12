package gov.cdc.nbs.accumulation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/** Provides {@link Collector} implementations to accumulate streams in to one or more objects. */
public class Accumulator {

  /**
   * Accumulates multiple {@code R} instances into a single {@code R} instance.
   *
   * @param merger The {@link BiFunction} that merges two values.
   * @param <S> The type for the value
   * @return An {@link Optional} containing the accumulated value or an empty {@link Optional} if no
   *     vales are accumulated.
   */
  public static <S> Collector<S, ?, Optional<S>> accumulating(final BinaryOperator<S> merger) {
    return Collectors.collectingAndThen(
        Collector.of(
            () -> new AccumulateAll<>(merger), AccumulateAll::accumulate, AccumulateAll::merge),
        a -> Optional.ofNullable(a.accumulated));
  }

  private static final class AccumulateAll<V> {

    private final BinaryOperator<V> merger;
    private V accumulated;

    private AccumulateAll(final BinaryOperator<V> merger, V accumulated) {
      this.merger = merger;
      this.accumulated = accumulated;
    }

    private AccumulateAll(final BinaryOperator<V> merger) {
      this(merger, null);
    }

    private void accumulate(final V item) {
      if (this.accumulated == null) {
        this.accumulated = item;
      } else {
        this.accumulated = this.merger.apply(this.accumulated, item);
      }
    }

    private AccumulateAll<V> merge(final AccumulateAll<V> other) {
      accumulate(other.accumulated);
      return new AccumulateAll<>(this.merger, this.accumulated);
    }
  }

  /**
   * Accumulates multiple {@code R} instances uniquely identified by an {@code S} into a single
   * list. Any accumulated values that resolve to the same identifier will be merged into one
   * instance.
   *
   * @param idResolver The {@link Function} that resolves an identifier from a value.
   * @param merger The {@link BiFunction} that merges two values.
   * @param <R> The type for the unique identifier
   * @param <S> The type for the value
   * @return A {@link List} of accumulated {@code V} instances.
   */
  public static <R, S> Collector<S, ?, List<S>> collecting(
      final Function<S, R> idResolver, final BinaryOperator<S> merger) {
    return Collectors.collectingAndThen(
        Collector.of(
            () -> new AccumulateById<>(idResolver, merger),
            AccumulateById::accumulate,
            AccumulateById::merge),
        AccumulateById::accumulated);
  }

  public static <R, S> Collector<S, ?, Optional<S>> accumulating(
      final Function<S, R> idResolver, final BinaryOperator<S> merger) {
    return Collectors.collectingAndThen(
        Collector.of(
            () -> new AccumulateById<>(idResolver, merger),
            AccumulateById::accumulate,
            AccumulateById::merge),
        a -> single(a.accumulated()));
  }

  private static <V> Optional<V> single(final List<V> items) {
    return items.isEmpty() ? Optional.empty() : Optional.of(items.get(0));
  }

  private static final class AccumulateById<I, V> {
    private final Function<V, I> idResolver;
    private final BinaryOperator<V> merger;
    private final Map<I, V> accumulated;

    private AccumulateById(final Function<V, I> idResolver, final BinaryOperator<V> merger) {
      this(idResolver, merger, new HashMap<>());
    }

    private AccumulateById(
        final Function<V, I> idResolver,
        final BinaryOperator<V> merger,
        final Map<I, V> accumulated) {
      this.idResolver = idResolver;
      this.merger = merger;
      this.accumulated = accumulated;
    }

    private AccumulateById<I, V> accumulate(final V item) {

      I identifier = this.idResolver.apply(item);

      this.accumulated.compute(
          identifier,
          (id, existing) -> existing == null ? item : this.merger.apply(existing, item));

      return this;
    }

    private AccumulateById<I, V> merge(final AccumulateById<I, V> other) {
      other.accumulated.forEach((id, item) -> this.accumulated.merge(id, item, this.merger));
      return this;
    }

    private List<V> accumulated() {
      return List.copyOf(accumulated.values());
    }
  }

  private Accumulator() {}
}

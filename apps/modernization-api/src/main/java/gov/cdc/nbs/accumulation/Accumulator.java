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

/**
 * Accumulates multiple {@code VALUE} instances uniquely identified by an {@code IDENTIFIER} into a single list.  Any
 * accumulated values that resolve to the same identifier will be merged into one instance.
 */
public class Accumulator<I, V> {

    /**
     * @param idResolver The {@link Function} that resolves an identifier from a value.
     * @param merger     The {@link BiFunction} that merges two values.
     * @param <R>        The type for the unique identifier
     * @param <S>        The type for the value
     * @return A {@link List} of accumulated {@code V} instances.
     */
    public static <R, S> Collector<S, ?, List<S>> collecting(
        final Function<S, R> idResolver, final BinaryOperator<S> merger
    ) {
        return Collectors.collectingAndThen(
            Collector.of(
                () -> new Accumulator<>(idResolver, merger),
                Accumulator::accumulate,
                Accumulator::merge
            ),
            Accumulator::accumulated
        );
    }

    public static <R, S> Collector<S, ?, Optional<S>> accumulating(
        final Function<S, R> idResolver, final BinaryOperator<S> merger
    ) {
        return Collectors.collectingAndThen(
            Collector.of(
                () -> new Accumulator<>(idResolver, merger),
                Accumulator::accumulate,
                Accumulator::merge
            ),
            a -> single(a.accumulated())
        );
    }

    private static <V> Optional<V> single(final List<V> items) {
        return items.isEmpty()
            ? Optional.empty()
            : Optional.of(items.get(0));
    }

    private final Function<V, I> idResolver;
    private final BinaryOperator<V> merger;
    private final Map<I, V> accumulated;

    private Accumulator(final Function<V, I> idResolver, final BinaryOperator<V> merger) {
        this(idResolver, merger, new HashMap<>());
    }

    private Accumulator(
        final Function<V, I> idResolver,
        final BinaryOperator<V> merger,
        final Map<I, V> accumulated
    ) {
        this.idResolver = idResolver;
        this.merger = merger;
        this.accumulated = accumulated;
    }

    private Accumulator<I, V> accumulate(final V item) {

        I identifier = this.idResolver.apply(item);

        this.accumulated.compute(
            identifier,
            (id, existing) -> existing == null
                ? item
                : this.merger.apply(existing, item)
        );

        return this;
    }

    private Accumulator<I, V> merge(final Accumulator<I, V> other) {
        other.accumulated.forEach((id, item) -> this.accumulated.merge(id, item, this.merger));
        return this;
    }

    List<V> accumulated() {
        return List.copyOf(accumulated.values());
    }

}

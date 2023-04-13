package gov.cdc.nbs.accumulation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Accumulates multiple {@code VALUE} instances uniquely identified by an {@code IDENTIFIER} into a single list.  Any
 * accumulated values that resolve to the same identifier will be merged into one instance.
 */
public class Accumulator<IDENTIFIER, VALUE> {

    /**
     * @param idResolver The {@link Function} that resolves an identifier from a value.
     * @param merger     The {@link BiFunction} that merges two values.
     * @param <I>        The type for the unique identifier
     * @param <V>        The type for the value
     * @return A {@link List} of accumulated {@code V} instances.
     */
    public static <I, V> Collector<V, ?, List<V>> collecting(
        final Function<V, I> idResolver, final BiFunction<V, V, V> merger
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

    public static <I, V> Collector<V, ?, Optional<V>> accumulating(
        final Function<V, I> idResolver, final BiFunction<V, V, V> merger
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

    private final Function<VALUE, IDENTIFIER> idResolver;
    private final BiFunction<VALUE, VALUE, VALUE> merger;
    private final Map<IDENTIFIER, VALUE> accumulated;

    private Accumulator(final Function<VALUE, IDENTIFIER> idResolver, final BiFunction<VALUE, VALUE, VALUE> merger) {
        this(idResolver, merger, new HashMap<>());
    }

    private Accumulator(
        final Function<VALUE, IDENTIFIER> idResolver,
        final BiFunction<VALUE, VALUE, VALUE> merger,
        final Map<IDENTIFIER, VALUE> accumulated
    ) {
        this.idResolver = idResolver;
        this.merger = merger;
        this.accumulated = accumulated;
    }

    private Accumulator<IDENTIFIER, VALUE> accumulate(final VALUE item) {

        IDENTIFIER identifier = this.idResolver.apply(item);

        this.accumulated.compute(
            identifier,
            (id, existing) -> existing == null
                ? item
                : this.merger.apply(existing, item)
        );

        return this;
    }

    private Accumulator<IDENTIFIER, VALUE> merge(final Accumulator<IDENTIFIER, VALUE> other) {
        other.accumulated.forEach((id, item) -> this.accumulated.merge(id, item, this.merger));
        return this;
    }

    List<VALUE> accumulated() {
        return List.copyOf(accumulated.values());
    }

}

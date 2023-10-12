package gov.cdc.nbs.testing.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Available<V> {

    public record Indexed<V>(int index, V item) {
    }


    private final Collection<V> items;

    public Available() {
        items = new ArrayList<>();
    }

    public void reset() {
        this.items.clear();
    }

    public void available(final V item) {
        this.items.add(item);
    }

    public Optional<V> maybeOne() {
        return this.items.stream().findFirst();
    }

    public V one() {
        return maybeOne()
            .orElseThrow(() -> new IllegalStateException("there are none available"));
    }

    public Stream<V> all() {
        return this.items.stream();
    }

    public Stream<Available.Indexed<V>> indexed() {
        AtomicInteger index = new AtomicInteger();
        return this.items.stream().map(v -> new Available.Indexed<>(index.getAndIncrement(), v));
    }

}

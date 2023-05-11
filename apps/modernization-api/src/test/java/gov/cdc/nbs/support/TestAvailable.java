package gov.cdc.nbs.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class TestAvailable<V> {

    public record Indexed<V>(int index, V item) {
    }


    private final Collection<V> items;

    public TestAvailable() {
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

    public Stream<TestAvailable.Indexed<V>> indexed() {
        AtomicInteger index = new AtomicInteger();
        return this.items.stream().map(v -> new TestAvailable.Indexed<>(index.getAndIncrement(), v));
    }

}

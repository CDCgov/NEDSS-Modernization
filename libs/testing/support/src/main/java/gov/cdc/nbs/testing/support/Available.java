package gov.cdc.nbs.testing.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
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

  public void include(final Collection<V> items) {
    this.items.addAll(items);
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

  public Stream<Indexed<V>> indexed() {
    AtomicInteger index = new AtomicInteger();
    return this.items.stream().map(v -> new Indexed<>(index.getAndIncrement(), v));
  }

  public Optional<V> random() {
    if (this.items.isEmpty()) {
      return Optional.empty();
    }

    int index = ThreadLocalRandom.current().nextInt(0, items.size());
    return this.items.stream()
        .skip(index)
        .findFirst();
  }
}

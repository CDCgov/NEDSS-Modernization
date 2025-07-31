package gov.cdc.nbs.testing.support;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class Available<V> {

  private static final SecureRandom RANDOM = new SecureRandom();


  public record Indexed<V>(int index, V item) {
  }


  private final List<V> items;

  public Available() {
    items = new ArrayList<>();
  }

  public Available(final V initial) {
    this();
    this.items.add(initial);
  }

  public void reset() {
    this.items.clear();
  }

  public void available(final V item) {
    this.items.add(item);
  }

  public void removeIf(final Predicate<V> filter) {
    this.items.removeIf(filter);
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

    int index = RANDOM.nextInt(0, items.size());
    return this.items.stream().skip(index).findFirst();
  }

  public void select(final Predicate<V> filter) {
    indexed().filter(item -> filter.test(item.item))
        .findFirst()
        .ifPresent(index -> {
          this.items.remove(index.index);
          this.items.addFirst(index.item);
        });
  }

  public void selected(final UnaryOperator<V> operator, final Supplier<V> initializer) {
    V current = this.items.isEmpty() ? Objects.requireNonNull(initializer).get() : this.items.getFirst();

    if (current != null) {
      V next = operator.apply(current);

      if (this.items.isEmpty()) {
        this.items.add(next);
      } else {
        this.items.set(0, next);
      }
    }
  }

}

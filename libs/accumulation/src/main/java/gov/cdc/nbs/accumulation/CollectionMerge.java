package gov.cdc.nbs.accumulation;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionMerge {

  /**
   * Creates a new {@link Stream} containing values from {@code left} and {@code right}.
   *
   * @param left A {@code Collection} of {@code V} elements
   * @param right A {@code Collection} of {@code V} elements
   * @param <V> The type of items to combine
   * @return A {@link Stream} of {@code V}
   */
  public static <V> Stream<V> merge(final Collection<V> left, final Collection<V> right) {
    return Stream.of(left, right).flatMap(Collection::stream);
  }

  /**
   * Creates a new {@link Collection} that contains distinct values from {@code left} and {@code
   * right}.
   *
   * @param left A {@code Collection} of {@code V} elements
   * @param right A {@code Collection} of {@code V} elements
   * @param <V> The type of items to combine
   * @return A {@link Collection} of {@code V}
   */
  public static <V> Collection<V> merged(final Collection<V> left, final Collection<V> right) {
    return merge(left, right).collect(Collectors.toSet());
  }

  private CollectionMerge() {
    //  static only
  }
}

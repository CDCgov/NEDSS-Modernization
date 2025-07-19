package gov.cdc.nbs.change;

import java.util.Collection;
import java.util.function.Function;

/**
 * Resolves the changes (additions, alterations, deletions) between two {@link java.util.Collection}s containing values
 * that can be paired using an identifying value.  The {@code left} collection is assumed to be the original state of the
 * values of the {@code right collection} while the {@code right} collection is assumed to be a possibly altered state of
 * the values of {@code left} collection.
 *
 * @param <LEFT>  The type for the left values.
 * @param <RIGHT> The type for the right values.
 * @param <ID>    The type of the identifier.
 */
public class ChangeResolver<LEFT, RIGHT, ID> {

  /**
   * Creates a {@code ChangeResolver} to resolve changes between two {@link java.util.Collection}s of the same type
   * with self identifying values.
   *
   * @param <X> The type of the values being resolved.
   * @return A simple {@link ChangeResolver} for types of {@code X}.
   */
  public static <X> ChangeResolver<X, X, X> simple() {
    return ofSameType(Function.identity());
  }

  /**
   * Creates a {@code ChangeResolver} to resolve changes between two {@link java.util.Collection}s of the same type
   * using the {@code toIdentifier} to identify each value.
   *
   * @param toIdentifier A {@link java.util.function.Function} to resolve the identifier of an instance of {@code V}.
   * @param <V>          The type of the values being resolved
   * @param <I>          The type of the identifier.
   * @return A {@link ChangeResolver} for types of {@code V}
   */
  public static <V, I> ChangeResolver<V, V, I> ofSameType(final Function<V, I> toIdentifier) {
    MatchResolver<V, V, I> resolver = new DefaultMatchResolver<>(toIdentifier, toIdentifier);
    return new ChangeResolver<>(resolver);
  }

  /**
   * Creates a {@code ChangeResolver} to resolve changes between a {@link java.util.Collection} of type {@code X} and
   * a {@link java.util.Collection} of type {@code Y}; using {@code leftToIdentifier} to identify left values, and
   * {@code rightToIdentifier} to identify right values.
   *
   * @param leftToIdentifier  A {@link java.util.function.Function} to resolve the identifier of an instance of {@code X}.
   * @param rightToIdentifier A {@link java.util.function.Function} to resolve the identifier of an instance of {@code Y}.
   * @param <X>               The type for the left values.
   * @param <Y>               The type for the right values.
   * @param <I>               The type of the identifier.
   * @return A {@link ChangeResolver} for types of {@code X} and {@code Y}.
   */
  public static <X, Y, I> ChangeResolver<X, Y, I> ofDifferingTypes(
      final Function<X, I> leftToIdentifier,
      final Function<Y, I> rightToIdentifier
  ) {
    MatchResolver<X, Y, I> resolver = new DefaultMatchResolver<>(leftToIdentifier, rightToIdentifier);
    return new ChangeResolver<>(resolver);
  }

  private final MatchResolver<LEFT, RIGHT, ID> resolver;

  /**
   * Creates a {@code ChangeResolver} to resolve changes using the {@code resolver}.
   *
   * @param resolver The {@link MatchResolver} used to pair values.
   */
  public ChangeResolver(final MatchResolver<LEFT, RIGHT, ID> resolver) {
    this.resolver = resolver;
  }

  /**
   * @param left  A {@link java.util.Collection} of {@code LEFT} values.
   * @param right A {@link java.util.Collection} of {@code RIGHT} values.
   * @return The {@link Changes}
   */
  public Changes<LEFT, RIGHT> resolve(final Collection<LEFT> left, final Collection<RIGHT> right) {
    return new Changes<>(resolver.resolve(left, right).toList());
  }

}

package gov.cdc.nbs.change;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Determines matches between Left and Right values by inserting values from each input collection
 * into respective {@link java.util.Map}s. Each Map is iterated over attempting to look up each
 * value in the other Map.
 *
 * @param <L> The type of the Left values
 * @param <R> The type of the Right values
 * @param <I> The type of identifier used to match Left and Right values
 */
class MatchResolver<L, R, I> {
  private final Function<L, I> leftToIdentifier;
  private final Function<R, I> rightToIdentifier;

  MatchResolver(final Function<L, I> leftToIdentifier, final Function<R, I> rightToIdentifier) {
    this.leftToIdentifier = leftToIdentifier;
    this.rightToIdentifier = rightToIdentifier;
  }

  public Stream<Match<L, R>> resolve(final Collection<L> lefts, final Collection<R> rights) {
    Map<I, L> leftMap = identified(leftToIdentifier, lefts);

    Map<I, R> rightMap = identified(rightToIdentifier, rights);

    return paired(leftMap, rightMap)
        //  values found in both maps will produce duplicate matches
        .distinct();
  }

  private Stream<Match<L, R>> paired(final Map<I, L> lefts, final Map<I, R> rights) {
    return Stream.concat(
        lefts.entrySet().stream().map(e -> Match.of(e.getValue(), rights.get(e.getKey()))),
        rights.entrySet().stream().map(e -> Match.of(lefts.get(e.getKey()), e.getValue())));
  }

  private <K, V> Map<K, V> identified(final Function<V, K> idMapper, final Collection<V> values) {
    return values.stream().collect(Collectors.toMap(idMapper, Function.identity()));
  }
}

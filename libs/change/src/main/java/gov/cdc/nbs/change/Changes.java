package gov.cdc.nbs.change;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class Changes<L, R> {

  private static <X, Y> Boolean notEqual(final X x, final Y y) {
    return !Objects.equals(x, y);
  }

  private static <L, R> Predicate<Match<L, R>> changed(final BiPredicate<L, R> hasChange) {
    return match -> match instanceof Match.Both<L, R>(L left, R right) && hasChange.test(left, right);
  }

  private final Collection<Match<L, R>> matches;

  Changes(final Collection<Match<L, R>> matches) {
    this.matches = matches;
  }

  public Stream<R> added() {
    return this.matches
        .stream()
        .filter(Match.OnlyRight.class::isInstance)
        .map(m -> ((Match.OnlyRight<L, R>) m).right());
  }

  public Stream<Match.Both<L, R>> altered() {
    return altered(Changes::notEqual);
  }

  public Stream<Match.Both<L, R>> altered(final BiPredicate<L, R> hasChange) {
    return this.matches
        .stream()
        .filter(changed(hasChange))
        .map(c -> (Match.Both<L, R>) c)
        .distinct();
  }

  public Stream<L> removed() {
    return matches
        .stream()
        .filter(Match.OnlyLeft.class::isInstance)
        .map(m -> ((Match.OnlyLeft<L, R>) m).left());
  }


}

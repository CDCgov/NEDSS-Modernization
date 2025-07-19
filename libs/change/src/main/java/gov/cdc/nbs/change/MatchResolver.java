package gov.cdc.nbs.change;

import java.util.Collection;
import java.util.stream.Stream;

public interface MatchResolver<L, R, I> {
  Stream<Match<L, R>> resolve(final Collection<L> lefts, final Collection<R> rights);
}

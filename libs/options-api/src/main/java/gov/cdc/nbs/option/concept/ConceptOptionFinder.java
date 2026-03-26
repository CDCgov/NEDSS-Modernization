package gov.cdc.nbs.option.concept;

import java.util.Collection;

public interface ConceptOptionFinder {

  /**
   * Returns all {@link ConceptOption} instances that are collected in a {@code valueSet}.
   *
   * @param valueSet The name of the value set to retrieve concepts for.
   * @param excluding any code to exclude.
   * @return All the concepts of the value set.
   */
  Collection<ConceptOption> find(final String valueSet, final String... excluding);
}

package gov.cdc.nbs.option.concept;

import java.util.Collection;

public interface ConceptOptionFinder {

  /**
   * Returns all {@link ConceptOption} instances that are collected in a {@code valueSet}.
   *
   * @param valueSet The name of the value set to retrieve concepts for.
   * @return All the concepts of the value set.
   */
  Collection<ConceptOption> find(final String valueSet);

}

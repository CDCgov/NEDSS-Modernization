package gov.cdc.nbs.concept;

import java.util.Collection;

public interface ConceptFinder {

  /**
   * Returns all {@link Concept} instances that are collected in a {@code valueSet}.
   *
   * @param valueSet The name of the value set to retrieve concepts for.
   * @return All the concepts of the value set.
   */
  Collection<Concept> find(final String valueSet);

}

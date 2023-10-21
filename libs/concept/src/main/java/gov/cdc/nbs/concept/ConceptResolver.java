package gov.cdc.nbs.concept;

import java.util.Collection;

public interface ConceptResolver {

  /**
   * @param valueSet The name of the value set to retrieve concepts for.
   * @param query    The query used to refine the returned concepts.
   * @param max      The maximum number of concepts to return.
   * @return Any of the concepts of the value set that match the {@code query} up to the {@code max}.
   */
  Collection<Concept> resolve(final String valueSet, final String query, final int max);

}

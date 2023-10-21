package gov.cdc.nbs.concept.rest;

import gov.cdc.nbs.concept.Concept;

import java.util.Collection;
import java.util.List;

class ConceptsResponseMapper {

  static ConceptsResponse asResponse(
      final String valueSet,
      final Collection<Concept> concepts
  ) {

    List<ConceptsResponse.Concept> values = concepts.stream()
        .map(ConceptsResponseMapper::asConcept)
        .toList();

    return new ConceptsResponse(valueSet, values);
  }

  private static ConceptsResponse.Concept asConcept(final Concept concept) {
    return new ConceptsResponse.Concept(
        concept.value(),
        concept.name(),
        concept.name(),
        concept.order()
    );
  }

  private ConceptsResponseMapper() {
  }
}

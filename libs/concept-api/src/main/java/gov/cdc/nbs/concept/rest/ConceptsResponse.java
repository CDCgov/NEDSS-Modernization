package gov.cdc.nbs.concept.rest;

import java.util.Collection;

record ConceptsResponse(String valueSet, Collection<Concept> concepts) {

  record Concept(String value, String name, String label, int order) {
  }

}

package gov.cdc.nbs.option.concept;

import gov.cdc.nbs.option.Option;
import java.util.Collection;
import java.util.List;

public class ConceptOptionsResponseMapper {

  public static ConceptOptionsResponse asResponse(
      final String valueSet, final Collection<ConceptOption> concepts) {

    List<Option> values = concepts.stream().map(ConceptOptionsResponseMapper::asConcept).toList();

    return new ConceptOptionsResponse(valueSet, values);
  }

  private static Option asConcept(final ConceptOption concept) {
    return new Option(concept.value(), concept.name(), concept.name(), concept.order());
  }

  private ConceptOptionsResponseMapper() {}
}

package gov.cdc.nbs.questionbank.support.concept;

import gov.cdc.nbs.testing.support.concept.ConceptParameterResolver;
import io.cucumber.java.ParameterType;

public class EventTypeSteps {

  private final ConceptParameterResolver resolver;

  EventTypeSteps(final ConceptParameterResolver resolver) {
    this.resolver = resolver;
  }

  @ParameterType(name = "eventType", value = ".*")
  public String eventType(final String value) {
    return resolver.resolve("BUS_OBJ_TYPE", value).orElse(null);
  }
}

package gov.cdc.nbs.questionbank.page;

import io.cucumber.java.ParameterType;

public class EventTypeSteps {

  private final EventTypeParameterResolver resolver;

  EventTypeSteps(final EventTypeParameterResolver resolver) {
    this.resolver = resolver;
  }

  @ParameterType(name = "eventType", value = ".*")
  public String eventType(final String value) {
    return resolver.resolve(value).orElse(null);
  }

}

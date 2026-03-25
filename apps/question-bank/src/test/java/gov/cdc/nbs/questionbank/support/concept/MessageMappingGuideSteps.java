package gov.cdc.nbs.questionbank.support.concept;

import gov.cdc.nbs.testing.support.concept.ConceptParameterResolver;
import io.cucumber.java.ParameterType;

public class MessageMappingGuideSteps {

  private final ConceptParameterResolver resolver;

  MessageMappingGuideSteps(final ConceptParameterResolver resolver) {
    this.resolver = resolver;
  }

  @ParameterType(name = "messageMappingGuide", value = ".*")
  public String messageMappingGuide(final String value) {
    return resolver.resolve("NBS_MSG_PROFILE", value).orElse(null);
  }

  @ParameterType(name = "mmg", value = ".*")
  public String mmg(final String value) {
    return resolver.resolve("NBS_MSG_PROFILE", value).orElse(null);
  }
}

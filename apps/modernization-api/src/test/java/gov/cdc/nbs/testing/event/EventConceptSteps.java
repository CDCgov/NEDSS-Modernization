package gov.cdc.nbs.testing.event;

import gov.cdc.nbs.testing.support.concept.ConceptParameterResolver;
import io.cucumber.java.ParameterType;

public class EventConceptSteps {

  private final ConceptParameterResolver resolver;

  EventConceptSteps(final ConceptParameterResolver resolver) {
    this.resolver = resolver;
  }

  @ParameterType(name = "priority", value = ".*")
  public String priority(final String value) {
    return resolver.resolve("NBS_PRIORITY", value).orElse(value);
  }

  @ParameterType(name = "disposition", value = ".*")
  public String disposition(final String value) {
    return resolver.resolve("NBS_DISPO", value).orElse(value);
  }

  @ParameterType(name = "processingDecision", value = ".*")
  public String processingDecision(final String value) {
    return resolver.resolve("NBS_NO_ACTION_RSN", value).orElse(value);
  }

  @ParameterType(name = "stdProcessingDecision", value = ".*")
  public String stdProcessingDecision(final String value) {
    return resolver.resolve("STD_NBS_PROCESSING_DECISION_ALL", value).orElse(value);
  }

  @ParameterType(name = "referralBasis", value = ".*")
  public String referralBasis(final String value) {
    return resolver.resolve("REFERRAL_BASIS", value).orElse(value);
  }
}

package gov.cdc.nbs.support.concept;

import gov.cdc.nbs.testing.support.concept.ConceptParameterResolver;
import io.cucumber.java.ParameterType;

public class EthnicitySteps {

  private final ConceptParameterResolver resolver;

  EthnicitySteps(final ConceptParameterResolver resolve) {
    this.resolver = resolve;
  }

  @ParameterType(name = "ethnicity", value = ".+")
  public String ethnicity(final String value) {
    return resolver.resolve("PHVS_ETHNICITYGROUP_CDC_UNK", value).orElse(null);
  }

  @ParameterType(name = "ethnicityUnknownReason", value = ".+")
  public String ethnicityUnknownReason(final String value) {
    return resolver.resolve("P_ETHN_UNK_REASON", value).orElse(null);
  }

  @ParameterType(name = "ethnicityDetail", value = ".+")
  public String ethnicityDetail(final String value) {
    return resolver.resolve("P_ETHN", value).orElse(null);
  }
}

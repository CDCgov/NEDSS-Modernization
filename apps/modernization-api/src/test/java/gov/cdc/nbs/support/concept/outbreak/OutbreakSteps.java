package gov.cdc.nbs.support.concept.outbreak;

import gov.cdc.nbs.testing.support.concept.ConceptMother;
import gov.cdc.nbs.testing.support.concept.ConceptParameterResolver;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;

public class OutbreakSteps {

  private static final String SET = "OUTBREAK_NM";
  private final ConceptParameterResolver resolver;
  private final ConceptMother mother;

  OutbreakSteps(final ConceptParameterResolver resolver, final ConceptMother mother) {
    this.resolver = resolver;
    this.mother = mother;
  }

  @Given("{string} caused an outbreak")
  public void x_caused_an_outbreak(final String name) {
    this.mother.create(SET, name);
  }

  @ParameterType(name = "outbreak", value = ".*")
  public String outbreak(final String value) {
    return resolver.resolve(SET, value).orElse(null);
  }
}

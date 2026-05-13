package gov.cdc.nbs.option.clinical.concept;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class ClinicalConceptListSteps {

  private final ClinicalConceptRequester requester;
  private final Active<ResultActions> response;

  ClinicalConceptListSteps(ClinicalConceptRequester requester, Active<ResultActions> response) {
    this.requester = requester;
    this.response = response;
  }

  @When("I request all clinical concepts for {valueSet} valueSet")
  public void counties(final String valueSet) throws Exception {
    response.active(this.requester.request(valueSet));
  }
}

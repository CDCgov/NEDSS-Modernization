package gov.cdc.nbs.questionbank.valueset.concept;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.web.servlet.ResultActions;

public class SearchConceptSteps {

  private final ConceptSearchRequester requester;
  private final Active<ResultActions> response;

  public SearchConceptSteps(
      final ConceptSearchRequester requester, final Active<ResultActions> response) {
    this.requester = requester;
    this.response = response;
  }

  @When("I search for concepts in {string} sorted by {string} {string}")
  public void concept_search(String valueset, String field, String order) throws Exception {
    Direction dir = order.toLowerCase().contains("asc") ? Direction.ASC : Direction.DESC;
    response.active(requester.search(valueset, PageRequest.of(0, 50, Sort.by(dir, field))));
  }

  @Then("the first concept returned is {string}")
  public void concepts_are_returned(String first) throws Exception {
    response
        .active()
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].localCode", equalTo(first)));
  }
}

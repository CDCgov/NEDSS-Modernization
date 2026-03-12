package gov.cdc.nbs.questionbank.question.available;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.ResultActions;

public class AvailableQuestionSearchSteps {

  private final Active<ResultActions> response;
  private final AvailableQuestionSearchRequester requester;
  private final PageMother pageMother;

  AvailableQuestionSearchSteps(
      final AvailableQuestionSearchRequester requester, final PageMother pageMother) {
    this.requester = requester;
    this.pageMother = pageMother;
    this.response = new Active<>();
  }

  @When("I search for available questions by {string} and sorted by {string} {direction}")
  public void i_search_for_available_questions(
      String query, String field, final Sort.Direction direction) throws Exception {
    PageRequest pageRequest = PageRequest.of(0, 2);

    if (!"none".equals(field)) {
      pageRequest = pageRequest.withSort((Sort.by(direction, field)));
    }
    response.active(
        requester.request(
            pageMother.one().getId(), pageRequest, new AvailableQuestionCriteria(query)));
  }

  @When("I search for available questions by {string}")
  public void i_search_for_available_question_by_query(String query) throws Exception {
    response.active(
        requester.request(
            pageMother.one().getId(), PageRequest.of(0, 2), new AvailableQuestionCriteria(query)));
  }

  @Then("a question with a {string} matching {string} is returned")
  public void a_question_with_matching_field_is_returned(String field, String value)
      throws Exception {
    String path = "$.content[0]." + field;

    response.active().andExpect(status().isOk()).andExpect(jsonPath(path, equalTo(value)));
  }

  @Then("the {string} available questions are returned")
  public void the_expected_available_questions_are_returned(String expected) throws Exception {
    String[] ids = expected.split(",");

    response
        .active()
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(ids.length)))
        .andExpect(jsonPath("$.content[0].uniqueId", equalTo(ids[0])))
        .andExpect(jsonPath("$.content[1].uniqueId", equalTo(ids[1])));
  }
}

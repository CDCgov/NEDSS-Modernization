package gov.cdc.nbs.questionbank.page.information;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.Matcher;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;

public class PageInformationSteps {

  private final Active<PageIdentifier> page;
  private final PageInformationRequester requester;
  private final Active<ResultActions> response;

  PageInformationSteps(
      final Active<PageIdentifier> page,
      final PageInformationRequester requester,
      final Active<ResultActions> response) {
    this.page = page;
    this.requester = requester;
    this.response = response;
  }

  @Before("@page-information")
  public void reset() {
    response.reset();
  }

  @When("I retrieve the information of a page")
  public void i_retrieve_the_information_of_a_page() {
    this.page
        .maybeActive()
        .map(PageIdentifier::id)
        .map(requester::request)
        .ifPresent(response::active);
  }

  @Then("the page information should have a(n) {string} equal to {string}")
  public void the_page_information_should_have_property_equal_to_value(
      final String property, final String value) throws Exception {
    JsonPathResultMatchers pathMatcher = matchingPath(property);
    this.response.active().andExpect(pathMatcher.value(matchingValue(property, value)));
  }

  @Then("the page information should not have a(n) {string} equal to {string}")
  public void the_page_information_should_not_have_property_equal_to_value(
      final String property, final String value) throws Exception {
    JsonPathResultMatchers pathMatcher = matchingPath(property);

    this.response.active().andExpect(pathMatcher.value(not(matchingValue(property, value))));
  }

  private JsonPathResultMatchers matchingPath(final String field) {
    return switch (field.toLowerCase()) {
      case "eventtype", "event type" -> jsonPath("$.eventType.name");
      case "messagemappingguide", "message mapping guide", "mmg" ->
          jsonPath("$.messageMappingGuide.name");
      case "name" -> jsonPath("$.name");
      case "datamart" -> jsonPath("$.datamart");
      case "description" -> jsonPath("$.description");
      case "condition" -> jsonPath("$.conditions[*].name");
      default ->
          throw new IllegalStateException("Unexpected Page Summary value: " + field.toLowerCase());
    };
  }

  private Matcher<?> matchingValue(final String property, final String value) {
    return switch (property.toLowerCase()) {
      case "condition", "conditions" -> hasItem(equalToIgnoringCase(value));
      default -> equalToIgnoringCase(value);
    };
  }
}

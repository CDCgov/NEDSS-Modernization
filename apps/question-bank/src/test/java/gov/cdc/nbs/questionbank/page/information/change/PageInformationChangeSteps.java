package gov.cdc.nbs.questionbank.page.information.change;

import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PageInformationChangeSteps {

  private final Active<PageIdentifier> page;
  private final Active<PageInformationChangeRequest> request;
  private final PageInformationChangeRequester requester;
  private final Active<ResultActions> response;

  PageInformationChangeSteps(
      final Active<PageIdentifier> page, final PageInformationChangeRequester requester) {
    this.page = page;
    this.requester = requester;
    this.request = new Active<>();
    this.response = new Active<>();
  }

  @Before("@page-information-change")
  public void clean() {
    request.active(new PageInformationChangeRequest());
    response.reset();
  }

  @Given("I want to change the page {string} to {string}")
  public void i_want_to_change_the_property_to_value(final String property, final String value) {
    switch (property.toLowerCase()) {
      case "description" -> this.request.active(active -> active.withDescription(value));
      case "name" -> this.request.active(active -> active.withName(value));
      case "datamart" -> this.request.active(active -> active.withDatamart(value));
      default -> throw new IllegalStateException("Unexpected Page value: " + property);
    }
  }

  @Given("I want to change the page Message Mapping Guide to {messageMappingGuide}")
  public void i_want_to_change_the_message_mapping_guide(final String value) {
    this.request.active(active -> active.withMessageMappingGuide(value));
  }

  @Given("I want to change the page associations to include {condition}")
  public void i_want_to_change_the_page_associations_to_include(final String value) {
    this.request.active(active -> active.withCondition(value));
  }

  @When("I change the page information")
  public void i_change_the_page_information() {
    this.response.active(this.requester.request(this.page.active().id(), this.request.active()));
  }

  @Then("the page information cannot be changed because {string}")
  public void the_page_information_cannot_be_changed(final String error) throws Exception {
    this.response
        .active()
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", equalToIgnoringCase(error)));
  }
}

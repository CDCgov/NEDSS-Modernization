package gov.cdc.nbs.questionbank.template;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class FindTemplateSteps {

  private final TemplateRequester requester;
  private final Active<ResultActions> response;
  private final PageMother mother;
  private final Active<PageIdentifier> page;

  FindTemplateSteps(
      final TemplateRequester requester,
      final PageMother mother,
      final Active<PageIdentifier> page) {
    this.requester = requester;
    this.mother = mother;
    this.page = page;
    this.response = new Active<>();
  }

  @Given("A template exists")
  public void a_template_exists() {
    mother.create("INV", "test-template", "GEN_Case_Map_v2.0");
    mother.template(page.active());
  }

  @When("I search for all templates")
  public void i_search_for_all_templates() throws Exception {
    response.active(requester.getAll());
  }

  @When("I search for all investigation templates")
  public void i_search_for_all_investigation_templates() throws Exception {
    response.active(requester.getAllInv());
  }

  @Then("templates are returned")
  public void templates_are_returned() throws Exception {
    response.active().andExpect(status().isOk()).andExpect(jsonPath("$.[*].id").exists());
  }
}

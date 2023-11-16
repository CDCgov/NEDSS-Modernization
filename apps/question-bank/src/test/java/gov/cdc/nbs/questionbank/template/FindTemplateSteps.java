package gov.cdc.nbs.questionbank.template;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import org.springframework.test.web.servlet.ResultActions;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.questionbank.template.response.Template;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class FindTemplateSteps {

  private final TemplateRequester requester;
  private final Active<ResultActions> response;
  private final ObjectMapper mapper;
  private final PageMother mother;
  private final Active<PageIdentifier> page;


  FindTemplateSteps(
      final TemplateRequester requester,
      final ObjectMapper mapper,
      final PageMother mother,
      final Active<PageIdentifier> page) {
    this.requester = requester;
    this.mapper = mapper;
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

  @Then("templates are returned")
  public void templates_are_returned() throws Exception {
    String content = response.active()
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();
    List<Template> templates = mapper.readValue(content, new TypeReference<List<Template>>() {});
    assertTrue(templates.size() > 0);
    templates.forEach(t -> assertNotNull(t.id()));
  }

}


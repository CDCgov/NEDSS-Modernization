package gov.cdc.nbs.questionbank.valueset.concept;

import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.support.valueset.ValueSetMother;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import java.util.List;
import org.springframework.test.web.servlet.ResultActions;

public class ListConceptsSteps {

  private final ValueSetMother mother;
  private final Active<Codeset> activeValueset;
  private final ListConceptsRequest request;
  private final Active<ResultActions> response;

  public ListConceptsSteps(
      final ValueSetMother mother,
      final Active<Codeset> activeValueset,
      final ListConceptsRequest request,
      final Active<ResultActions> response) {
    this.mother = mother;
    this.activeValueset = activeValueset;
    this.request = request;
    this.response = response;
  }

  @Given("the value set has a concept named {string} with value {string}")
  public void a_concept_exists(String name, String value) {
    mother.addConcept(activeValueset.active(), name, value);
  }

  @When("I list concepts for the {string} value set")
  public void i_list_concepts_for_value_set(String valueset) throws Exception {
    response.active(request.send(valueset));
  }

  @Then("I find these concepts:")
  public void i_find_concepts(final DataTable dataTable) throws Exception {
    List<String> names = new ArrayList<>();
    List<String> values = new ArrayList<>();
    dataTable
        .asMaps()
        .forEach(
            row -> {
              names.add(row.get("name"));
              values.add(row.get("value"));
            });
    response
        .active()
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[*].display", contains(names.toArray())))
        .andExpect(jsonPath("$.[*].localCode", contains(values.toArray())));
  }
}

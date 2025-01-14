package gov.cdc.nbs.questionbank.valueset;

import com.google.common.collect.Comparators;
import com.jayway.jsonpath.JsonPath;
import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.support.valueset.ValueSetMother;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetSearchRequest;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Comparator;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ValueSetOptionsSteps {

  private final Active<Codeset> valueset;
  private final ValueSetMother mother;
  private final Active<ResultActions> response;
  private final ValueSetOptionRequester request;

  public ValueSetOptionsSteps(
      final ValueSetMother mother,
      final Active<ResultActions> response,
      final ValueSetOptionRequester request,
      final Active<Codeset> activeValueset) {
    this.valueset = activeValueset;
    this.mother = mother;
    this.response = response;
    this.request = request;
  }

  @Given("I have a valueset named {string}")
  public void i_have_a_valueset_named(String name) {
    valueset.active(mother.valueSet(name));
  }

  @Given("the valueset is {string}")
  public void the_valueset_is_status(String status) {
    Codeset vs = mother.setActive(valueset.active(), "active".equalsIgnoreCase(status));
    valueset.active(vs);
  }

  @When("I list all valueset options")
  public void i_list_all_valueset_options() throws Exception {
    response.active(request.findAll());
  }

  @When("I search for valuesets with query {string} sorted by {string} {string}")
  public void i_search_for_valuesets_by_query(String query, String sortField, String direction) throws Exception {
    Pageable pageable = PageRequest.of(0, 25, direction.contains("asc") ? Direction.ASC : Direction.DESC, sortField);
    response.active(request.search(new ValueSetSearchRequest(query), pageable));
  }

  @Then("{string} is in the valueset options")
  public void entry_is_in_the_valueset_options(String name) throws Exception {
    response.active()
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[*].name").value(hasItem(name)));
  }

  @Then("{string} is in the valueset search results")
  public void entry_is_in_the_valueset_search_results(String name) throws Exception {
    response.active()
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content.[*].name").value(hasItem(name)));
  }

  @Then("{string} is not in the valueset options")
  public void entry_is_not_in_the_valueset_options(String name) throws Exception {
    response.active()
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[*].name").value(not(hasItem(name))));
  }

  @Then("the valueset options are sorted by {string} {string}")
  public void options_are_sorted_by(String field, String direction) throws Exception {
    String content = response.active()
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();

    boolean isAsc = direction.contains("asc");
    List<String> results = JsonPath.read(content, "$.content.[*]." + field);
    Comparator<String> comparator = (a, b) -> isAsc ? a.compareToIgnoreCase(b) : b.compareToIgnoreCase(a);
    assertTrue(Comparators.isInOrder(results, comparator));
  }
}

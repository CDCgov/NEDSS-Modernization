package gov.cdc.nbs.questionbank.valueset;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.ResultActions;
import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.support.valueset.ValueSetMother;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ValueSetOptionsSteps {

  private final Active<Codeset> valueset;
  private final ValueSetMother mother;
  private final Active<ResultActions> response;
  private final ValueSetOptionRequester request;

  public ValueSetOptionsSteps(
      final ValueSetMother mother,
      final Active<ResultActions> response,
      final ValueSetOptionRequester request) {
    this.valueset = new Active<>();
    this.mother = mother;
    this.response = response;
    this.request = request;
  }

  @Given("I have a valueset named {string}")
  public void i_have_a_valueset_named(String name) {
    valueset.active(mother.valueSet(name));
  }

  @Given("the valueset is {string}")
  public void the_valueset_is_status(String status) throws IllegalAccessException {
    Codeset vs = mother.setActive(valueset.active(), "active".equals(status.toLowerCase()));
    valueset.active(vs);
  }

  @When("I list all valueset options")
  public void i_list_all_valueset_options() throws Exception {
    response.active(request.request());
  }

  @Then("{string} is in the valueset options")
  public void entry_is_in_the_valueset_options(String name) throws Exception {
    response.active()
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[*].name").value(hasItem(name)));
  }

  @Then("{string} is not in the valueset options")
  public void entry_is_not_in_the_valueset_options(String name) throws Exception {
    response.active()
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[*].name").value(not(hasItem(name))));
  }
}

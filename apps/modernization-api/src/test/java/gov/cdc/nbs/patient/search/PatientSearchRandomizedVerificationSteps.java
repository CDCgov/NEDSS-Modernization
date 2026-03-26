package gov.cdc.nbs.patient.search;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

public class PatientSearchRandomizedVerificationSteps {

  private final Active<SearchablePatient> searchable;
  private final Active<ResultActions> results;

  PatientSearchRandomizedVerificationSteps(
      final Active<SearchablePatient> searchable, final Active<ResultActions> results) {
    this.searchable = searchable;
    this.results = results;
  }

  @Then("I find the patient")
  public void i_find_the_patient() throws Exception {
    this.results
        .active()
        .andExpect(
            jsonPath("$.data.findPatientsByFilter.content[*].patient")
                .value(hasItem((int) this.searchable.active().identifier())));
  }
}

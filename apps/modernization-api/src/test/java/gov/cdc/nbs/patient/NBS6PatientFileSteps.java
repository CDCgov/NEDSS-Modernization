package gov.cdc.nbs.patient;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.classic.NBS6Server;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NBS6PatientFileSteps {


  private final NBS6Server nbs6;
  private final Active<PatientIdentifier> activePatient;
  private final NBS6PatientFileRequester requester;
  private final Active<ResultActions> response;

  NBS6PatientFileSteps(

      final NBS6Server nbs6,
      final Active<PatientIdentifier> activePatient,
      final NBS6PatientFileRequester requester,
      final Active<ResultActions> response
  ) {

    this.nbs6 = nbs6;
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @When("I navigate to the NBS6 Patient file")
  public void i_navigate_to_the_nbs6_patient_file() {
    nbs6.using(
        (server, url) -> server.expect(requestTo(url + "/nbs/HomePage.do?method=patientSearchSubmit"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess())
    );

    this.activePatient.maybeActive()
        .map(this.requester::file)
        .ifPresent(this.response::active);
  }

  @Then("NBS6 is prepared to show the Patient file")
  public void nbs6_is_prepared_to_show_the_Patient_file() {
    nbs6.verify();
  }

  @Then("I am redirected to the NBS6 Patient file")
  public void i_am_redirected_to_the_nbs6_patient_file() throws Exception {
    String expected = activePatient.maybeActive()
        .map(identifier -> "/nbs/PatientSearchResults1.do?ContextAction=ViewFile&uid=%d".formatted(identifier.id()))
        .orElse("invalid");

    this.response.active()
        .andExpect(status().isSeeOther())
        .andExpect(header().string("Location", equalTo(expected)));
  }

}

package gov.cdc.nbs.patient.profile.investigation;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PatientProfileViewInvestigationSteps {


  private final String classicUrl;
  private final MockRestServiceServer server;

  private final Active<PatientIdentifier> activePatient;

  private final Active<InvestigationIdentifier> activeInvestigation;

  private final Authenticated authenticated;

  private final MockMvc mvc;

  private final Active<ResultActions> response;

  PatientProfileViewInvestigationSteps(
      @Value("${nbs.wildfly.url:http://wildfly:7001}") final String classicUrl,
      @Qualifier("classicRestService") final MockRestServiceServer server,
      final Active<PatientIdentifier> activePatient,
      final Active<InvestigationIdentifier> activeInvestigation,
      final Authenticated authenticated,
      final MockMvc mvc,
      final Active<ResultActions> response
  ) {
    this.classicUrl = classicUrl;
    this.server = server;
    this.activePatient = activePatient;
    this.activeInvestigation = activeInvestigation;
    this.authenticated = authenticated;
    this.mvc = mvc;
    this.response = response;
  }

  @Before
  public void clearServer() {
    server.reset();
  }

  @When("the investigation is viewed from the Patient Profile")
  public void the_investigation_is_viewed_from_the_patient_profile() throws Exception {
    long patient = activePatient.active().id();

    server.expect(
            requestTo(classicUrl + "/nbs/HomePage.do?method=patientSearchSubmit"))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    server.expect(requestTo(classicUrl + "/nbs/PatientSearchResults1.do?ContextAction=ViewFile&uid=" + patient))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    long investigation = activeInvestigation.active().identifier();

    String request =
        "/nbs/api/profile/%d/investigation/%d".formatted(
            patient,
            investigation);

    response.active(
        mvc.perform(authenticated.withUser(MockMvcRequestBuilders.get(request))));
  }

  @Then("the classic profile is prepared to view an investigation")
  public void the_classic_profile_is_prepared_to_view_an_investigation() {
    server.verify();
  }

  @Then("I am redirected to Classic NBS to view an Investigation")
  public void i_am_redirected_to_classic_nbs_to_view_an_investigation() throws Exception {
    long patient = activePatient.active().id();
    long investigation = activeInvestigation.active().identifier();

    String expected = "/nbs/ViewFile1.do?ContextAction=InvestigationIDOnSummary&publicHealthCaseUID="
        + investigation;

    this.response.active()
        .andExpect(status().isTemporaryRedirect())
        .andExpect(header().string("Location", containsString(expected)))
        .andExpect(cookie().value("Return-Patient", String.valueOf(patient)));
  }

  @Then("I am not allowed to view a Classic NBS Investigation")
  public void i_am_not_allowed_to_view_a_classic_nbs_investigation() throws Exception {
    this.response.active().andExpect(status().isForbidden());
  }
}

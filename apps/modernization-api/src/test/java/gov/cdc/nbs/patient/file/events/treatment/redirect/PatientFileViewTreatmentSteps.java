package gov.cdc.nbs.patient.file.events.treatment.redirect;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.event.treatment.TreatmentIdentifier;
import gov.cdc.nbs.testing.interaction.http.AuthenticatedMvcRequester;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class PatientFileViewTreatmentSteps {

  private final String classicUrl;
  private final Active<PatientIdentifier> activePatient;
  private final Active<TreatmentIdentifier> activeTreatment;

  private final AuthenticatedMvcRequester authenticated;
  private final Active<ResultActions> activeResponse;


  @Qualifier("classicRestService")
  private final MockRestServiceServer server;

  PatientFileViewTreatmentSteps(
      @Value("${nbs.wildfly.url:http://wildfly:7001}") final String classicUrl,
      final Active<PatientIdentifier> activePatient,
      final Active<TreatmentIdentifier> activeTreatment,
      final AuthenticatedMvcRequester authenticated,
      final Active<ResultActions> activeResponse,
      final MockRestServiceServer server
  ) {
    this.classicUrl = classicUrl;
    this.activePatient = activePatient;
    this.activeTreatment = activeTreatment;
    this.authenticated = authenticated;
    this.activeResponse = activeResponse;
    this.server = server;
  }

  @Before
  public void reset() {
    server.reset();
  }

  @When("the Treatment is viewed from the Patient file")
  public void viewed() throws Exception {
    long patient = activePatient.active().id();

    server.expect(
            requestTo(classicUrl + "/nbs/HomePage.do?method=patientSearchSubmit"))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    server.expect(requestTo(classicUrl + "/nbs/PatientSearchResults1.do?ContextAction=ViewFile&uid=" + patient))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    long treatment = activeTreatment.active().identifier();

    activeResponse.active(
        authenticated.request(MockMvcRequestBuilders.get("/nbs/api/patients/{patient}/treatments/{identifier}", patient, treatment)));
  }

  @Then("NBS is prepared to view a Treatment")
  public void sessionIsPrepared() {
    server.verify();
  }

  @Then("I am redirected to Classic NBS to view a Treatment")
  public void redirected() throws Exception {
    long patient = activePatient.active().id();
    long treatment = activeTreatment.active().identifier();

    String expected = "/nbs/PageAction.do?method=viewGenericLoad&businessObjectType=TRMT&actUid=" + treatment;

    activeResponse.active()
        .andExpect(MockMvcResultMatchers.header().string("Location", expected))
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
        .andExpect(MockMvcResultMatchers.cookie().value("Return-Patient", String.valueOf(patient)));

    MockHttpServletResponse response = activeResponse.active().andReturn().getResponse();

    assertThat(response.getRedirectedUrl()).contains(expected);

    assertThat(response.getCookies())
        .satisfiesOnlyOnce(cookie -> {
          assertThat(cookie.getName()).isEqualTo("Return-Patient");
          assertThat(cookie.getValue()).isEqualTo(String.valueOf(patient));
        });
  }

}

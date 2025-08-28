package gov.cdc.nbs.patient.profile.vaccination;

import gov.cdc.nbs.authentication.SessionCookie;
import gov.cdc.nbs.patient.TestPatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class PatientProfileViewVaccinationSteps {


  private final String classicUrl;

  private final TestPatientIdentifier patients;

  private final Active<VaccinationIdentifier> vaccinations;

  private final MockMvc mvc;

  private final Active<SessionCookie> activeSession;

  private final Active<MockHttpServletResponse> activeResponse;


  private final MockRestServiceServer server;

  PatientProfileViewVaccinationSteps(
      final TestPatientIdentifier patients,
      final Active<VaccinationIdentifier> vaccinations,
      final MockMvc mvc,
      final Active<SessionCookie> activeSession,
      final Active<MockHttpServletResponse> activeResponse,
      @Qualifier("classicRestService") final MockRestServiceServer server,
      @Value("${nbs.wildfly.url:http://wildfly:7001}") final String classicUrl
  ) {
    this.patients = patients;
    this.vaccinations = vaccinations;
    this.mvc = mvc;
    this.activeSession = activeSession;
    this.activeResponse = activeResponse;
    this.server = server;
    this.classicUrl = classicUrl;
  }

  @Before
  public void reset() {
    server.reset();
  }

  @When("the Vaccination is viewed from the Patient Profile")
  public void the_vaccination_is_viewed_from_the_patient_profile() throws Exception {
    long patient = patients.one().id();

    server.expect(
            requestTo(classicUrl + "/nbs/HomePage.do?method=patientSearchSubmit"))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    server.expect(requestTo(classicUrl + "/nbs/PatientSearchResults1.do?ContextAction=ViewFile&uid=" + patient))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    long vaccination = vaccinations.active().identifier();

    String request =
        "/nbs/api/profile/%d/vaccination/%d".formatted(
            patient,
            vaccination);

    activeResponse.active(
        mvc.perform(
                MockMvcRequestBuilders.get(request)
                    .cookie(activeSession.active().asCookie()))
            .andReturn()
            .getResponse());
  }

  @Then("the classic profile is prepared to view a Vaccination")
  public void the_classic_profile_is_prepared_to_view_a_vaccination() {
    server.verify();
  }

  @Then("I am redirected to Classic NBS to view a Vaccination")
  public void i_am_redirected_to_classic_nbs_to_view_a_vaccination() {
    long patient = patients.one().id();
    long vaccination = vaccinations.active().identifier();

    String expected = "/nbs/PageAction.do?method=viewGenericLoad&businessObjectType=VAC&Action=DSFilePath&actUid="
        + vaccination;

    MockHttpServletResponse response = activeResponse.active();

    assertThat(response.getRedirectedUrl()).contains(expected);

    assertThat(response.getCookies())
        .satisfiesOnlyOnce(cookie -> {
          assertThat(cookie.getName()).isEqualTo("Return-Patient");
          assertThat(cookie.getValue()).isEqualTo(String.valueOf(patient));
        });
  }

  @Then("I am not allowed to view a Classic NBS Vaccination")
  public void i_am_not_allowed_to_view_a_classic_nbs_vaccination() {
    MockHttpServletResponse response = activeResponse.active();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
  }
}

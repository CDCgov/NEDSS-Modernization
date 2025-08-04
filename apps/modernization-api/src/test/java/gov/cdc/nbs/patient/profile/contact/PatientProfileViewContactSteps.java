package gov.cdc.nbs.patient.profile.contact;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.event.contact.ContactRecordIdentifier;
import gov.cdc.nbs.testing.interaction.http.AuthenticatedMvcRequester;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class PatientProfileViewContactSteps {

  private final Active<PatientIdentifier> activePatient;
  private final Active<ContactRecordIdentifier> activeContactRecord;
  private final AuthenticatedMvcRequester authenticated;
  private final String classicUrl;
  private final Active<MockHttpServletResponse> activeResponse;
  private final MockRestServiceServer server;

  PatientProfileViewContactSteps(
      final Active<PatientIdentifier> activePatient,
      final Active<ContactRecordIdentifier> activeContactRecord,
      final AuthenticatedMvcRequester authenticated,
      @Value("${nbs.wildfly.url:http://wildfly:7001}") final String classicUrl,
      final Active<MockHttpServletResponse> activeResponse,
      @Qualifier("classicRestService") final MockRestServiceServer server
  ) {
    this.activePatient = activePatient;
    this.activeContactRecord = activeContactRecord;
    this.authenticated = authenticated;
    this.classicUrl = classicUrl;
    this.activeResponse = activeResponse;
    this.server = server;
  }

  @Before
  public void reset() {
    server.reset();
  }

  @When("the Contact is viewed from the Patient Profile")
  public void the_contact_is_viewed_from_the_patient_profile() {
    long patient = activePatient.active().id();

    server.expect(
            requestTo(classicUrl + "/nbs/HomePage.do?method=patientSearchSubmit"))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    server.expect(requestTo(classicUrl + "/nbs/PatientSearchResults1.do?ContextAction=ViewFile&uid=" + patient))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    long contact = activeContactRecord.active().identifier();

    String request =
        "/nbs/api/profile/%d/contact/%d".formatted(
            patient,
            contact);

    activeResponse.active(
        authenticated.request(MockMvcRequestBuilders.get(request)
                .queryParam("condition", "condition-value")
            ).andReturn()
            .getResponse()
    );
  }

  @Then("the classic profile is prepared to view a Contact")
  public void the_classic_profile_is_prepared_to_view_a_contact() {
    server.verify();
  }

  @Then("I am redirected to Classic NBS to view a Contact")
  public void i_am_redirected_to_classic_nbs_to_view_a_contact() {
    long patient = activePatient.active().id();
    long contact = activeContactRecord.active().identifier();

    String expected = "/nbs/ContactTracing.do?method=viewContact&mode=View&Action=DSFilePath&contactRecordUid="
        + contact + "&DSInvestigationCondition=condition-value";

    MockHttpServletResponse response = activeResponse.active();

    assertThat(response.getRedirectedUrl()).contains(expected);

    assertThat(response.getCookies())
        .satisfiesOnlyOnce(cookie -> {
          assertThat(cookie.getName()).isEqualTo("Return-Patient");
          assertThat(cookie.getValue()).isEqualTo(String.valueOf(patient));
        });
  }

  @Then("I am not allowed to view a Classic NBS Contact")
  public void i_am_not_allowed_to_view_a_classic_nbs_contact() {
    MockHttpServletResponse response = activeResponse.active();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
  }
}

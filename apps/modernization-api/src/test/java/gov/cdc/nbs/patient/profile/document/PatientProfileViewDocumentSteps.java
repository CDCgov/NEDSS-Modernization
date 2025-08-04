package gov.cdc.nbs.patient.profile.document;

import gov.cdc.nbs.authentication.SessionCookie;
import gov.cdc.nbs.event.document.CaseReportIdentifier;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
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

public class PatientProfileViewDocumentSteps {


  private final String classicUrl;

  private final Active<PatientIdentifier> patients;

  private final Active<CaseReportIdentifier> documents;

  private final MockMvc mvc;

  private final Active<SessionCookie> activeSession;

  private final Active<MockHttpServletResponse> activeResponse;

  private final MockRestServiceServer server;

  PatientProfileViewDocumentSteps(
      @Value("${nbs.wildfly.url:http://wildfly:7001}") final String classicUrl,
      final Active<PatientIdentifier> patients,
      final Active<CaseReportIdentifier> documents,
      final MockMvc mvc,
      final Active<SessionCookie> activeSession,
      final Active<MockHttpServletResponse> activeResponse,
      @Qualifier("classicRestService") final MockRestServiceServer server
  ) {
    this.classicUrl = classicUrl;
    this.patients = patients;
    this.documents = documents;
    this.mvc = mvc;
    this.activeSession = activeSession;
    this.activeResponse = activeResponse;
    this.server = server;
  }

  @Before
  public void reset() {
    server.reset();
  }

  @When("the Document is viewed from the Patient Profile")
  public void the_document_is_viewed_from_the_patient_profile() throws Exception {
    long patient = patients.active().id();

    server.expect(
            requestTo(classicUrl + "/nbs/HomePage.do?method=patientSearchSubmit"))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    server.expect(requestTo(classicUrl + "/nbs/PatientSearchResults1.do?ContextAction=ViewFile&uid=" + patient))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    long document = documents.active().identifier();

    String request =
        "/nbs/api/profile/%d/document/%d".formatted(
            patient,
            document);

    activeResponse.active(
        mvc.perform(
                MockMvcRequestBuilders.get(request)
                    .cookie(activeSession.active().asCookie()))
            .andReturn()
            .getResponse());
  }

  @Then("the classic profile is prepared to view a Document")
  public void the_classic_profile_is_prepared_to_view_a_document() {
    server.verify();
  }

  @Then("I am redirected to Classic NBS to view a Document")
  public void i_am_redirected_to_classic_nbs_to_view_a_document() {
    long patient = patients.active().id();
    long document = documents.active().identifier();

    String expected = "/nbs/ViewFile1.do?ContextAction=DocumentIDOnEvents&nbsDocumentUid=" + document;

    MockHttpServletResponse response = activeResponse.active();

    assertThat(response.getRedirectedUrl()).contains(expected);

    assertThat(response.getCookies())
        .satisfiesOnlyOnce(cookie -> {
          assertThat(cookie.getName()).isEqualTo("Return-Patient");
          assertThat(cookie.getValue()).isEqualTo(String.valueOf(patient));
        });
  }

  @Then("I am not allowed to view a Classic NBS Document")
  public void i_am_not_allowed_to_view_a_classic_nbs_document() {
    MockHttpServletResponse response = activeResponse.active();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
  }

}

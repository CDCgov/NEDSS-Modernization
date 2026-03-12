package gov.cdc.nbs.patient.profile.report.morbidity;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gov.cdc.nbs.authentication.SessionCookie;
import gov.cdc.nbs.patient.TestPatients;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.servlet.http.Cookie;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class SubmitMorbidityReportSteps {

  @Value("${nbs.wildfly.url:http://wildfly:7001}")
  String classicUrl;

  @Autowired TestPatients patients;

  @Autowired MockMvc mvc;

  @Autowired Active<SessionCookie> activeSession;

  @Autowired Active<ResultActions> response;

  @Autowired
  @Qualifier("classicRestService") MockRestServiceServer server;

  @Before
  public void reset() {
    response.reset();
    server.reset();
  }

  @When("a morbidity report is submitted from Classic NBS")
  public void a_morbidity_report_is_submitted_from_classic_nbs() throws Exception {

    server
        .expect(requestTo(classicUrl + "/nbs/AddObservationMorb2.do"))
        .andExpect(method(HttpMethod.POST))
        .andExpect(
            content()
                .multipartDataContains(
                    Map.of(
                        "ContextAction", "Submit",
                        "other-data", "value")))
        .andRespond(withSuccess());

    long patient = patients.one();

    SessionCookie session = activeSession.maybeActive().orElse(new SessionCookie(null));

    response.active(
        mvc.perform(
            MockMvcRequestBuilders.multipart("/nbs/redirect/patient/report/morbidity/submit")
                .part(new MockPart("ContextAction", "Submit".getBytes()))
                .part(new MockPart("other-data", "value".getBytes()))
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .cookie(session.asCookie())
                .cookie(new Cookie("Return-Patient", String.valueOf(patient)))));
  }

  @Then("the morbidity report is submitted to Classic NBS")
  public void the_morbidity_report_is_submitted_to_classic_nbs() {
    server.verify();
  }

  @When("a morbidity report is submitted and the user has chosen to also create an investigation")
  public void
      a_morbidity_report_is_submitted_and_the_user_has_chosen_to_also_create_an_investigation()
          throws Exception {
    long patient = patients.one();

    SessionCookie session = activeSession.maybeActive().orElse(new SessionCookie(null));

    response.active(
        mvc.perform(
            MockMvcRequestBuilders.multipart("/nbs/redirect/patient/report/morbidity/submit")
                .part(new MockPart("ContextAction", "SubmitAndCreateInvestigation".getBytes()))
                .part(new MockPart("other-data", "value".getBytes()))
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .cookie(session.asCookie())
                .cookie(new Cookie("Return-Patient", String.valueOf(patient)))));
  }

  @Then("I am redirected to Classic NBS to Create an Investigation from the Morbidity Report")
  public void i_am_redirected_to_classic_nbs_to_create_an_investigation_from_the_morbidity_report()
      throws Exception {
    this.response
        .active()
        .andExpect(status().isTemporaryRedirect())
        .andExpect(
            header()
                .string(
                    "Location",
                    containsString(
                        "/nbs/AddObservationMorb2.do?ContextAction=SubmitAndCreateInvestigation")));
  }
}

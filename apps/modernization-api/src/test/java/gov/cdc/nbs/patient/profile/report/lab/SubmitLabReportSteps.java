package gov.cdc.nbs.patient.profile.report.lab;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

public class SubmitLabReportSteps {

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
    server.reset();
  }

  @When("a lab report is submitted from Classic NBS")
  public void a_lab_report_is_submitted_from_classic_nbs() throws Exception {

    server
        .expect(requestTo(classicUrl + "/nbs/AddObservationLab2.do"))
        .andExpect(method(HttpMethod.POST))
        .andExpect(
            content()
                .formDataContains(
                    Map.of(
                        "ContextAction", "Submit",
                        "other-data", "value")))
        .andRespond(withSuccess());

    long patient = patients.one();

    SessionCookie session = activeSession.maybeActive().orElse(new SessionCookie(null));

    response.active(
        mvc.perform(
            post("/nbs/redirect/patient/report/lab/submit")
                .param("ContextAction", "Submit")
                .param("other-data", "value")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .cookie(session.asCookie())
                .cookie(new Cookie("Return-Patient", String.valueOf(patient)))));
  }

  @Then("the lab report is submitted to Classic NBS")
  public void the_lab_report_is_submitted_to_classic_nbs() {
    server.verify();
  }
}

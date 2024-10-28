package gov.cdc.nbs.patient.profile.investigation;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteInvestigationSteps {

  private final String classicUrl;

  private final Active<PatientIdentifier> activePatient;

  private final MockMvc mvc;

  private final Authenticated authenticated;

  private final Active<ResultActions> response;

  private final MockRestServiceServer server;

  DeleteInvestigationSteps(
      @Qualifier("classicRestService") final MockRestServiceServer server,
      final Active<ResultActions> response,
      final Authenticated authenticated,
      final MockMvc mvc,
      final Active<PatientIdentifier> activePatient,
      @Value("${nbs.wildfly.url}") final String classicUrl
  ) {
    this.server = server;
    this.response = response;
    this.authenticated = authenticated;
    this.mvc = mvc;
    this.activePatient = activePatient;
    this.classicUrl = classicUrl;
  }

  @When("an investigation is deleted from Classic NBS")
  public void an_investigation_is_deleted_from_classic_nbs() throws Exception {

    server.expect(requestTo(classicUrl + "/nbs/PageAction.do"))
        .andExpect(method(HttpMethod.POST))
        .andExpect(
            content().formDataContains(
                Map.of(
                    "ContextAction", "ReturnToFileSummary",
                    "method", "deleteSubmit",
                    "other-data", "value"
                )
            )
        )
        .andRespond(withSuccess());

    long patient = activePatient.active().id();

    response.active(
        mvc.perform(
            authenticated.withSession(post("/nbs/redirect/patient/investigation/delete"))
                .param("ContextAction", "ReturnToFileSummary")
                .param("method", "deleteSubmit")
                .param("other-data", "value")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .cookie(new Cookie("Return-Patient", String.valueOf(patient)))));
  }

  @When("an investigation with an associated lab report is deleted from NBS6")
  public void an_investigation_with_a_lab_report_is_deleted_from_classic_nbs() throws Exception {

    server.expect(requestTo(classicUrl + "/nbs/PageAction.do"))
        .andExpect(method(HttpMethod.POST))
        .andExpect(
            content().formDataContains(
                Map.of(
                    "ContextAction", "FileSummary",
                    "method", "deleteSubmit",
                    "other-data", "value"
                )
            )
        )
        .andRespond(withStatus(HttpStatus.FOUND).header("Location", "/nbs6/investigation"));

    long patient = activePatient.active().id();

    response.active(
        mvc.perform(
            authenticated.withSession(post("/nbs/redirect/patient/investigation/delete"))
                .param("ContextAction", "FileSummary")
                .param("method", "deleteSubmit")
                .param("other-data", "value")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .cookie(new Cookie("Return-Patient", String.valueOf(patient))))
    );
  }

  @Then("the investigation delete is submitted to NBS6")
  public void the_investigation_delete_is_submitted_to_classic_nbs() {
    server.verify();
  }

  @Then("I am returned to the investigation in NBS6")
  public void i_am_returned_to_the_investigation_in_nbs6() throws Exception {
    this.response.active()
        .andExpect(status().isFound())
        .andExpect(header().string("Location", startsWith("/nbs6/investigation")));
  }
}

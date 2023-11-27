package gov.cdc.nbs.patient.profile.investigation;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.Cookie;
import java.util.Map;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class DeleteInvestigationSteps {

  @Value("${nbs.wildfly.url:http://wildfly:7001}")
  String classicUrl;

  @Autowired
  Active<PatientIdentifier> activePatient;

  @Autowired
  MockMvc mvc;

  @Autowired
  Authenticated authenticated;

  @Autowired
  Active<ResultActions> response;

  @Autowired
  @Qualifier("classic")
  MockRestServiceServer server;

  @Before
  public void reset() {
    server.reset();
  }

  @When("an investigation is deleted from Classic NBS")
  public void an_investigation_is_deleted_from_classic_nbs() throws Exception {

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
        .andRespond(withSuccess());

    long patient = activePatient.active().id();

    response.active(
        mvc.perform(
            authenticated.withSession(post("/nbs/redirect/patient/investigation/delete"))
                .param("ContextAction", "FileSummary")
                .param("method", "deleteSubmit")
                .param("other-data", "value")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .cookie(new Cookie("Return-Patient", String.valueOf(patient)))
        )
    );
  }

  @Then("the investigation delete is submitted to Classic NBS")
  public void the_investigation_delete_is_submitted_to_classic_nbs() {
    server.verify();
  }
}

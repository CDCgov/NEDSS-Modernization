package gov.cdc.nbs.patient.profile.investigation.merge;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
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

public class PatientProfileMergeInvestigationSteps {

  @Value("${nbs.wildfly.url:http://wildfly:7001}")
  String classicUrl;

  @Autowired Active<PatientIdentifier> activePatient;

  @Autowired MockMvc mvc;

  @Autowired Active<ResultActions> activeResponse;

  @Autowired Authenticated authenticated;

  @Autowired
  @Qualifier("classicRestService") MockRestServiceServer server;

  @Before
  public void reset() {
    server.reset();
  }

  @When("an investigation is merged from Classic NBS")
  public void an_investigation_is_merged_from_classic_nbs() throws Exception {

    server
        .expect(requestTo(classicUrl + "/nbs/PageAction.do"))
        .andExpect(method(HttpMethod.POST))
        .andExpect(
            content()
                .formDataContains(
                    Map.of(
                        "ContextAction", "Submit",
                        "method", "mergeSubmit")))
        .andRespond(withSuccess());

    long patient = activePatient.active().id();

    activeResponse.active(
        mvc.perform(
            authenticated
                .withSession(post("/nbs/redirect/patient/investigation/merge"))
                .param("ContextAction", "Submit")
                .param("method", "mergeSubmit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .cookie(new Cookie("Return-Patient", String.valueOf(patient)))));
  }

  @Then("the investigation merge is submitted to Classic NBS")
  public void the_investigation_merge_is_submitted_to_classic_nbs() {
    server.verify();
  }
}

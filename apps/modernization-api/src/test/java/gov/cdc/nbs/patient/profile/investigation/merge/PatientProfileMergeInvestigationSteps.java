package gov.cdc.nbs.patient.profile.investigation.merge;

import gov.cdc.nbs.authentication.SessionCookie;
import gov.cdc.nbs.patient.TestPatients;
import gov.cdc.nbs.support.TestActive;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.Cookie;
import java.util.Map;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class PatientProfileMergeInvestigationSteps {

    @Value("${nbs.wildfly.url:http://wildfly:7001}")
    String classicUrl;

    @Autowired
    TestPatients patients;

    @Autowired
    MockMvc mvc;

    @Autowired
    TestActive<SessionCookie> activeSession;

    @Autowired
    TestActive<MockHttpServletResponse> activeResponse;

    @Autowired
    @Qualifier("classic")
    MockRestServiceServer server;

    @Before
    public void reset() {
        server.reset();
    }

    @When("an investigation is merged from Classic NBS")
    public void an_investigation_is_merged_from_classic_nbs() throws Exception {

        server.expect(
                requestTo(classicUrl + "/nbs/PageAction.do"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(
                        content().formDataContains(
                                Map.of(
                                        "ContextAction", "Submit",
                                        "method", "mergeSubmit")))
                .andRespond(withSuccess());

        long patient = patients.one();

        SessionCookie session = activeSession.maybeActive().orElse(new SessionCookie(null));

        activeResponse.active(
                mvc
                        .perform(
                                MockMvcRequestBuilders.post("/nbs/redirect/patient/investigation/merge")
                                        .param("ContextAction", "Submit")
                                        .param("method", "mergeSubmit")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                        .cookie(session.asCookie())
                                        .cookie(new Cookie("Return-Patient", String.valueOf(patient)))

                        )
                        .andReturn()
                        .getResponse());

    }

    @Then("the investigation merge is submitted to Classic NBS")
    public void the_investigation_merge_is_submitted_to_classic_nbs() {
        server.verify();
    }
}

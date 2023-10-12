package gov.cdc.nbs.patient.profile.investigation;

import gov.cdc.nbs.authentication.SessionCookie;
import gov.cdc.nbs.patient.TestPatients;
import gov.cdc.nbs.testing.support.Active;
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

public class DeleteInvestigationSteps {

    @Value("${nbs.wildfly.url:http://wildfly:7001}")
    String classicUrl;

    @Autowired
    TestPatients patients;

    @Autowired
    MockMvc mvc;

    @Autowired
    Active<SessionCookie> activeSession;

    @Autowired
    Active<MockHttpServletResponse> activeResponse;

    @Autowired
    @Qualifier("classic")
    MockRestServiceServer server;

    @Before
    public void reset() {
        server.reset();
    }

    @When("an investigation is deleted from Classic NBS")
    public void an_investigation_is_deleted_from_classic_nbs() throws Exception {

        server.expect(
                requestTo(classicUrl + "/nbs/PageAction.do"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(
                        content().formDataContains(
                                Map.of(
                                        "ContextAction", "FileSummary",
                                        "method", "deleteSubmit",
                                        "other-data", "value")))
                .andRespond(withSuccess());

        long patient = patients.one();

        SessionCookie session = activeSession.maybeActive().orElse(new SessionCookie(null));

        activeResponse.active(
                mvc
                        .perform(
                                MockMvcRequestBuilders.post("/nbs/redirect/patient/investigation/delete")
                                        .param("ContextAction", "FileSummary")
                                        .param("method", "deleteSubmit")
                                        .param("other-data", "value")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                        .cookie(session.asCookie())
                                        .cookie(new Cookie("Return-Patient", String.valueOf(patient)))

                        )
                        .andReturn()
                        .getResponse());
    }

    @Then("the investigation delete is submitted to Classic NBS")
    public void the_investigation_delete_is_submitted_to_classic_nbs() {
        server.verify();
    }
}

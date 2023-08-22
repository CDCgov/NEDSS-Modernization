package gov.cdc.nbs.patient.profile.investigation;

import gov.cdc.nbs.authorization.SessionCookie;
import gov.cdc.nbs.patient.TestPatients;
import gov.cdc.nbs.support.TestActive;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class DeleteLegacyInvestigationSteps {

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
        activeResponse.reset();
        server.reset();
    }

    @When("a legacy investigation is deleted from Classic NBS")
    public void a_legacy_investigation_is_deleted_from_classic_nbs() throws Exception {

        server.expect(
                requestTo(classicUrl + "/nbs/ViewInvestigation1.do?ContextAction=ReturnToFileSummary&delete=true")
            )
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess())
        ;

        long patient = patients.one();

        SessionCookie session = activeSession.maybeActive().orElse(new SessionCookie(null));


        activeResponse.active(
            mvc
                .perform(
                    MockMvcRequestBuilders.get("/nbs/redirect/patient/investigation/delete")
                        .param("ContextAction", "ReturnToFileSummary")
                        .param("delete", "true")
                        .cookie(session.asCookie())
                        .cookie(new Cookie("Returning-Patient", String.valueOf(patient)))

                )
                .andReturn()
                .getResponse()
        );
    }

    @When("a newly created legacy investigation is deleted from Classic NBS")
    public void a_newly_created_legacy_investigation_is_deleted_from_classic_nbs() throws Exception {

        server.expect(
                requestTo(classicUrl + "/nbs/ViewInvestigation1.do?ContextAction=ReturnToFileEvents&delete=true")
            )
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess())
        ;

        long patient = patients.one();

        SessionCookie session = activeSession.maybeActive().orElse(new SessionCookie(null));


        activeResponse.active(
            mvc
                .perform(
                    MockMvcRequestBuilders.get("/nbs/redirect/patient/investigation/delete")
                        .param("ContextAction", "ReturnToFileEvents")
                        .param("delete", "true")
                        .cookie(session.asCookie())
                        .cookie(new Cookie("Returning-Patient", String.valueOf(patient)))

                )
                .andReturn()
                .getResponse()
        );
    }

    @Then("the legacy investigation delete is submitted to Classic NBS")
    public void the__legacy_investigation_delete_is_submitted_to_classic_nbs() {
        server.verify();
    }
}

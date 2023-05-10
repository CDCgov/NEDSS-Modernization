package gov.cdc.nbs.patient.profile.report.morbidity;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.Cookie;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class SubmitMorbidityReportSteps {

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

    @When("a morbidity report is submitted from Classic NBS")
    public void a_morbidity_report_is_submitted_from_classic_nbs() throws Exception {

        server.expect(
                requestTo(classicUrl + "/nbs/AddObservationMorb2.do")
            )
            .andExpect(method(HttpMethod.POST))
            .andExpect(
                content().multipartDataContains(
                    Map.of(
                        "ContextAction", "Submit",
                        "other-data", "value"
                    )
                )
            )
            .andRespond(withSuccess())
        ;

        long patient = patients.one();

        SessionCookie session = activeSession.maybeActive().orElse(new SessionCookie(null));


        activeResponse.active(
            mvc
                .perform(
                    MockMvcRequestBuilders.multipart("/nbs/redirect/patient/report/morbidity/submit")
                        .part(new MockPart("ContextAction", "Submit".getBytes()))
                        .part(new MockPart("other-data", "value".getBytes()))
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .cookie(session.asCookie())
                        .cookie(new Cookie("Returning-Patient", String.valueOf(patient)))

                )
                .andReturn()
                .getResponse()
        );
    }

    @Then("the morbidity report is submitted to Classic NBS")
    public void the_morbidity_report_is_submitted_to_classic_nbs() {
        server.verify();
    }

    @When("a morbidity report is submitted and the user has chosen to also create an investigation")
    public void a_morbidity_report_is_submitted_and_the_user_has_chosen_to_also_create_an_investigation()
        throws Exception {
        long patient = patients.one();

        SessionCookie session = activeSession.maybeActive().orElse(new SessionCookie(null));


        activeResponse.active(
            mvc
                .perform(
                    MockMvcRequestBuilders.multipart("/nbs/redirect/patient/report/morbidity/submit")
                        .part(new MockPart("ContextAction", "SubmitAndCreateInvestigation".getBytes()))
                        .part(new MockPart("other-data", "value".getBytes()))
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .cookie(session.asCookie())
                        .cookie(new Cookie("Returning-Patient", String.valueOf(patient)))

                ).andReturn()
                .getResponse()
        );
    }

    @Then("I am redirected to Classic NBS to Create an Investigation from the Morbidity Report")
    public void i_am_redirected_to_classic_nbs_to_create_an_investigation_from_the_morbidity_report() {

        MockHttpServletResponse response = activeResponse.active();

        assertThat(response.getRedirectedUrl()).contains(
            "/nbs/AddObservationMorb2.do?ContextAction=SubmitAndCreateInvestigation");

        assertThat(response.getStatus()).isEqualTo(HttpStatus.TEMPORARY_REDIRECT.value());


    }
}

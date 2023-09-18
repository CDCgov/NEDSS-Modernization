package gov.cdc.nbs.patient.profile.vaccination;

import gov.cdc.nbs.authentication.SessionCookie;
import gov.cdc.nbs.patient.TestPatientIdentifier;
import gov.cdc.nbs.support.TestActive;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class PatientProfileSubmitVaccinationSteps {

    @Value("${nbs.wildfly.url:http://wildfly:7001}")
    String classicUrl;

    @Autowired
    TestPatientIdentifier patients;

    @Autowired
    MockMvc mvc;

    @Autowired
    TestActive<SessionCookie> activeSession;

    @Autowired
    TestActive<MockHttpServletResponse> activeResponse;

    @Autowired
    TestActive<UserDetails> activeUserDetails;

    @Autowired
    @Qualifier("classic")
    MockRestServiceServer server;

    @Before
    public void reset() {
        activeResponse.reset();
        server.reset();
    }

    @When("a Vaccination is added from a Patient Profile")
    public void the_vaccination_is_viewed_from_the_patient_profile() throws Exception {
        long patient = patients.one().id();

        server.expect(
                requestTo(classicUrl + "/nbs/HomePage.do?method=patientSearchSubmit")
            )
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess())
        ;

        server.expect(requestTo(classicUrl + "/nbs/PatientSearchResults1.do?ContextAction=ViewFile&uid=" + patient))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess())
        ;

        String request = String.format("/nbs/api/profile/%d/vaccination/", patient);

        activeResponse.active(
            mvc.perform(
                    MockMvcRequestBuilders.get(request)
                        .with(user(activeUserDetails.active()))
                        .cookie(activeSession.active().asCookie())
                )
                .andReturn()
                .getResponse()
        );
    }



    @Then("the classic profile is prepared to add a Vaccination")
    public void the_classic_profile_is_prepared_to_add_a_vaccination() {
        server.verify();
    }


    @Then("I am redirected to Classic NBS to add a Vaccination")
    public void i_am_redirected_to_classic_nbs_to_add_a_vaccination() {
        long patient = patients.one().id();

        String expected =
            "/nbs/PageAction.do?method=createGenericLoad&businessObjectType=VAC&Action=DSFilePath";

        MockHttpServletResponse response = activeResponse.active();

        assertThat(response.getRedirectedUrl()).contains(expected);

        assertThat(response.getCookies())
            .satisfiesOnlyOnce(cookie -> {
                    assertThat(cookie.getName()).isEqualTo("Returning-Patient");
                    assertThat(cookie.getValue()).isEqualTo(String.valueOf(patient));
                }
            );
    }


    @Then("I am not allowed to add a Classic NBS Vaccination")
    public void i_am_not_allowed_to_add_a_classic_nbs_vaccination() {
        MockHttpServletResponse response = activeResponse.active();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }
}

package gov.cdc.nbs.patient.profile.vaccination;

import gov.cdc.nbs.authentication.SessionCookie;
import gov.cdc.nbs.patient.TestPatientIdentifier;
import gov.cdc.nbs.support.Active;
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

public class PatientProfileViewVaccinationSteps {

    @Value("${nbs.wildfly.url:http://wildfly:7001}")
    String classicUrl;

    @Autowired
    TestPatientIdentifier patients;

    @Autowired
    TestVaccinations vaccinations;

    @Autowired
    MockMvc mvc;

    @Autowired
    Active<SessionCookie> activeSession;

    @Autowired
    Active<MockHttpServletResponse> activeResponse;

    @Autowired
    Active<UserDetails> activeUserDetails;

    @Autowired
    @Qualifier("classic")
    MockRestServiceServer server;

    @Before
    public void reset() {
        activeResponse.reset();
        server.reset();
    }

    @When("the Vaccination is viewed from the Patient Profile")
    public void the_vaccination_is_viewed_from_the_patient_profile() throws Exception {
        long patient = patients.one().id();

        server.expect(
                requestTo(classicUrl + "/nbs/HomePage.do?method=patientSearchSubmit"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess());

        server.expect(requestTo(classicUrl + "/nbs/PatientSearchResults1.do?ContextAction=ViewFile&uid=" + patient))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess());

        long vaccination = vaccinations.one();

        String request = String.format(
                "/nbs/api/profile/%d/vaccination/%d",
                patient,
                vaccination);

        activeResponse.active(
                mvc.perform(
                        MockMvcRequestBuilders.get(request)
                                .with(user(activeUserDetails.active()))
                                .cookie(activeSession.active().asCookie()))
                        .andReturn()
                        .getResponse());
    }

    @Then("the classic profile is prepared to view a Vaccination")
    public void the_classic_profile_is_prepared_to_view_a_vaccination() {
        server.verify();
    }

    @Then("I am redirected to Classic NBS to view a Vaccination")
    public void i_am_redirected_to_classic_nbs_to_view_a_vaccination() {
        long patient = patients.one().id();
        long vaccination = vaccinations.one();

        String expected = "/nbs/PageAction.do?method=viewGenericLoad&businessObjectType=VAC&Action=DSFilePath&actUid="
                + vaccination;

        MockHttpServletResponse response = activeResponse.active();

        assertThat(response.getRedirectedUrl()).contains(expected);

        assertThat(response.getCookies())
                .satisfiesOnlyOnce(cookie -> {
                    assertThat(cookie.getName()).isEqualTo("Return-Patient");
                    assertThat(cookie.getValue()).isEqualTo(String.valueOf(patient));
                });
    }

    @Then("I am not allowed to view a Classic NBS Vaccination")
    public void i_am_not_allowed_to_view_a_classic_nbs_vaccination() {
        MockHttpServletResponse response = activeResponse.active();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }
}

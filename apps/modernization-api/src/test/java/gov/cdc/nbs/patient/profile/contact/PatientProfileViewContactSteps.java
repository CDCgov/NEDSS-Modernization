package gov.cdc.nbs.patient.profile.contact;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.DefaultResponseCreator;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import gov.cdc.nbs.authentication.SessionCookie;
import gov.cdc.nbs.patient.TestPatientIdentifier;
import gov.cdc.nbs.patient.contact.TestContactTracings;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PatientProfileViewContactSteps {

    @Value("${nbs.wildfly.url:http://wildfly:7001}")
    String classicUrl;

    @Autowired
    TestPatientIdentifier patients;

    @Autowired
    TestContactTracings tracings;

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
        server.reset();
    }

    @When("the Contact is viewed from the Patient Profile")
    public void the_contact_is_viewed_from_the_patient_profile() throws Exception {
        long patient = patients.one().id();

        server.expect(
                requestTo(classicUrl + "/nbs/HomePage.do?method=patientSearchSubmit"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess());

        server.expect(requestTo(classicUrl + "/nbs/PatientSearchResults1.do?ContextAction=ViewFile&uid=" + patient))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess());

        long contact = tracings.one();

        String request = String.format(
                "/nbs/api/profile/%d/contact/%d",
                patient,
                contact);

        activeResponse.active(
                mvc.perform(
                        MockMvcRequestBuilders.get(request)
                                .queryParam("condition", "condition-value")
                                .cookie(activeSession.active().asCookie()))
                        .andReturn()
                        .getResponse());
    }

    @Then("the classic profile is prepared to view a Contact")
    public void the_classic_profile_is_prepared_to_view_a_contact() {
        server.verify();
    }

    @Then("I am redirected to Classic NBS to view a Contact")
    public void i_am_redirected_to_classic_nbs_to_view_a_contact() {
        long patient = patients.one().id();
        long contact = tracings.one();

        String expected = "/nbs/ContactTracing.do?method=viewContact&mode=View&Action=DSFilePath&contactRecordUid="
                + contact + "&DSInvestigationCondition=condition-value";

        MockHttpServletResponse response = activeResponse.active();

        assertThat(response.getRedirectedUrl()).contains(expected);

        assertThat(response.getCookies())
                .satisfiesOnlyOnce(cookie -> {
                    assertThat(cookie.getName()).isEqualTo("Return-Patient");
                    assertThat(cookie.getValue()).isEqualTo(String.valueOf(patient));
                });
    }

    @Then("I am not allowed to view a Classic NBS Contact")
    public void i_am_not_allowed_to_view_a_classic_nbs_contact() {
        MockHttpServletResponse response = activeResponse.active();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }
}

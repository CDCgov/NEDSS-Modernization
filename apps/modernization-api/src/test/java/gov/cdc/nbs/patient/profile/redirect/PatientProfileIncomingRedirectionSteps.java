package gov.cdc.nbs.patient.profile.redirect;

import gov.cdc.nbs.authorization.TestActiveSession;
import gov.cdc.nbs.authorization.TestActiveUser;
import gov.cdc.nbs.support.TestActive;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.Cookie;

import static org.assertj.core.api.Assertions.assertThat;

public class PatientProfileIncomingRedirectionSteps {

    @Autowired
    MockMvc mvc;

    @Autowired
    TestActiveSession activeSession;

    @Autowired
    TestActiveUser activeUser;

    @Autowired
    TestActive<MockHttpServletResponse> activeResponse;

    @Before
    public void reset() {
        activeResponse.reset();
    }

    @When("Redirecting a Classic Master Patient Record Profile")
    public void redirecting_a_classic_master_patient_record_profile() throws Exception {
        String session = activeSession.maybeActive().orElse(null);
        activeResponse.active(
            mvc
                .perform(
                    MockMvcRequestBuilders.post("/nbs/redirect/patientProfile")
                        .param("MPRUid", "3179")
                        .cookie(new Cookie("JSESSIONID", session)))
                .andReturn()
                .getResponse()
        );
    }

    @When("Redirecting a Classic Revision Patient Profile")
    public void redirecting_a_classic_revision_patient_profile() throws Exception {
        String session = activeSession.maybeActive().orElse(null);
        activeResponse.active(
            mvc
                .perform(
                    MockMvcRequestBuilders.post("/nbs/redirect/patientProfile")
                        .param("uid", "3179")
                        .cookie(new Cookie("JSESSIONID", session)))
                .andReturn()
                .getResponse()
        );
    }

    @Then("I am redirected to the Modernized Patient Profile")
    public void i_am_redirected_to_the_modernized_patient_profile() {
        assertThat(activeResponse.active().getStatus()).isEqualTo(HttpStatus.SEE_OTHER.value());

        assertThat(activeResponse.active().getRedirectedUrl()).contains("/patient-profile/3179");
    }
}

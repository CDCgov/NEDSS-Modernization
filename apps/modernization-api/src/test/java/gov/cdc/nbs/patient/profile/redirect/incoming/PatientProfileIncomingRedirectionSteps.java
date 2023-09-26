package gov.cdc.nbs.patient.profile.redirect.incoming;

import gov.cdc.nbs.authentication.SessionCookie;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.TestActive;
import gov.cdc.nbs.support.TestAvailable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;

public class PatientProfileIncomingRedirectionSteps {

    @Autowired
    TestAvailable<PatientIdentifier> patients;

    @Autowired
    MockMvc mvc;

    @Autowired
    TestActive<SessionCookie> activeSession;

    @Autowired
    TestActive<MockHttpServletResponse> activeResponse;

    @When("Redirecting a Classic Master Patient Record Profile")
    public void redirecting_a_classic_master_patient_record_profile() throws Exception {

        PatientIdentifier patient = patients.one();

        SessionCookie session = activeSession.maybeActive().orElse(new SessionCookie(null));

        activeResponse.active(
                mvc
                        .perform(
                                MockMvcRequestBuilders.post("/nbs/redirect/patientProfile")
                                        .param("MPRUid", String.valueOf(patient.id()))
                                        .cookie(session.asCookie())
                        )
                        .andReturn()
                        .getResponse()
        );
    }

    @When("Redirecting a Classic Revision Patient Profile")
    public void redirecting_a_classic_revision_patient_profile() throws Exception {
        PatientIdentifier patient = patients.one();

        SessionCookie session = activeSession.maybeActive().orElse(new SessionCookie(null));

        activeResponse.active(
                mvc
                        .perform(
                                MockMvcRequestBuilders.post("/nbs/redirect/patientProfile")
                                        .param("uid", String.valueOf(patient.id()))
                                        .cookie(session.asCookie())
                        )
                        .andReturn()
                        .getResponse()
        );
    }

    @Then("I am redirected to the Modernized Patient Profile")
    public void i_am_redirected_to_the_modernized_patient_profile() {
        PatientIdentifier patient = patients.one();

        MockHttpServletResponse response = activeResponse.active();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.SEE_OTHER.value());
        assertThat(response.getRedirectedUrl()).contains("/patient-profile/" + patient.shortId());

        assertThat(response.getCookie("Return-Patient").getValue()).isEmpty();
        assertThat(response.getCookie("Patient-Action").getValue()).isEmpty();
    }
}

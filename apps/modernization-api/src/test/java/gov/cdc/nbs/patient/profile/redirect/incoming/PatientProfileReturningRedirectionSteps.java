package gov.cdc.nbs.patient.profile.redirect.incoming;

import gov.cdc.nbs.authentication.SessionCookie;
import gov.cdc.nbs.patient.TestPatients;
import gov.cdc.nbs.support.Active;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.Cookie;

public class PatientProfileReturningRedirectionSteps {

    @Autowired
    TestPatients patients;

    @Autowired
    MockMvc mvc;

    @Autowired
    Active<SessionCookie> activeSession;

    @Autowired
    Active<MockHttpServletResponse> activeResponse;

    @When("Returning to a Patient Profile")
    public void returning_to_a_patient_profile() throws Exception {

        long patient = patients.one();

        SessionCookie session = activeSession.maybeActive().orElse(new SessionCookie(null));

        activeResponse.active(
                mvc
                        .perform(
                                MockMvcRequestBuilders.get("/nbs/redirect/patientProfile/return")
                                        .cookie(session.asCookie())
                                        .cookie(new Cookie("Return-Patient", String.valueOf(patient))))
                        .andReturn()
                        .getResponse()
        );
    }

    @When("Returning to a Patient Profile {string} tab")
    public void returning_to_a_patient_profile_tab(final String tab) throws Exception {

        long patient = patients.one();

        SessionCookie session = activeSession.maybeActive().orElse(new SessionCookie(null));

        activeResponse.active(
                mvc
                        .perform(
                                MockMvcRequestBuilders.get("/nbs/redirect/patientProfile/" + tab + "/return")
                                        .cookie(session.asCookie())
                                        .cookie(new Cookie("Return-Patient", String.valueOf(patient))))
                        .andReturn()
                        .getResponse());
    }
}

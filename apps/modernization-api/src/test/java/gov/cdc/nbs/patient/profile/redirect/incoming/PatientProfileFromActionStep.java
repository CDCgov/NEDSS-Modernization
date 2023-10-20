package gov.cdc.nbs.patient.profile.redirect.incoming;

import gov.cdc.nbs.authentication.SessionCookie;
import gov.cdc.nbs.event.search.investigation.TestInvestigations;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.Cookie;

public class PatientProfileFromActionStep {

    @Autowired
    TestInvestigations investigations;

    @Autowired
    MockMvc mvc;

    @Autowired
    Active<SessionCookie> activeSession;

    @Autowired
    Active<MockHttpServletResponse> activeResponse;

    @When("Navigating to a Patient Profile from a {string}")
    @When("Navigating to a Patient Profile from an {string}")
    public void navigating_to_a_patient_profile_from_an_action(final String actionType) throws Exception {

        long actionIdentifier = resolveActionIdentifier(actionType);

        SessionCookie session = activeSession.maybeActive().orElse(new SessionCookie(null));

        activeResponse.active(
                mvc
                        .perform(
                                MockMvcRequestBuilders.get("/nbs/redirect/patientProfile/return")
                                        .cookie(session.asCookie())
                                        .cookie(new Cookie("Patient-Action", String.valueOf(actionIdentifier))))
                        .andReturn()
                        .getResponse()
        );
    }

    private long resolveActionIdentifier(final String action) {
        return switch (action.toLowerCase()) {
            case "investigation" -> investigations.one();
            default -> throw new IllegalStateException("Unexpected value: " + action);
        };
    }
}

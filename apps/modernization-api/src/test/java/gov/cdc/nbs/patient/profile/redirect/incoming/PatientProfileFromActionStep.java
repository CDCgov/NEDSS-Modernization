package gov.cdc.nbs.patient.profile.redirect.incoming;

import gov.cdc.nbs.event.search.investigation.TestInvestigations;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class PatientProfileFromActionStep {

  @Autowired
  TestInvestigations investigations;

  @Autowired
  MockMvc mvc;

  @Autowired
  Authenticated authenticated;

  @Autowired
  Active<ResultActions> result;

  @When("Navigating to a Patient Profile from a(n) {string}")
  public void navigating_to_a_patient_profile_from_an_action(final String actionType) throws Exception {

    long actionIdentifier = resolveActionIdentifier(actionType);


    result.active(
        mvc.perform(
            authenticated.withSession(get("/nbs/redirect/patientProfile/return"))
                .cookie(new Cookie("Patient-Action", String.valueOf(actionIdentifier))))
    );
  }

  private long resolveActionIdentifier(final String action) {
    return switch (action.toLowerCase()) {
      case "investigation" -> investigations.one();
      default -> throw new IllegalStateException("Unexpected value: " + action);
    };
  }
}

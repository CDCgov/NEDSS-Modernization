package gov.cdc.nbs.patient.profile.summary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.profile.PatientProfile;
import gov.cdc.nbs.support.TestActive;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PatientSummarySteps {

    @Autowired
    private TestActive<PatientIdentifier> patient;

    @Autowired
    private PatientSummaryResolver summaryResolver;

    @Autowired
    private TestActive<PatientSummary> summary;

    private Exception exception;

    @Before
    public void clear() {
        this.patient.reset();
        this.summary.reset();
        this.exception = null;
    }

    @When("a patient summary is requested by patient identifier")
    public void a_patient_summary_is_requested_by_patient_identifier() {
        var profile = new PatientProfile(patient.active().id(), patient.active().local(), (short) 0);
        try {
            summary.active(summaryResolver.resolve(profile, null).orElse(null));
        } catch (Exception e) {
            this.exception = e;
        }
    }

    @Then("the summary is found")
    public void the_summary_is_found() {
        assertNotNull(summary.active());
    }

    @Then("the summary is not accessible")
    public void the_summary_is_not_accessible() {
        assertTrue(summary.maybeActive().isEmpty());
        assertThat(this.exception).isInstanceOf(AccessDeniedException.class);
    }
}

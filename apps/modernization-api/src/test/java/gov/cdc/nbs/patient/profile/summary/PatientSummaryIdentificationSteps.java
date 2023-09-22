package gov.cdc.nbs.patient.profile.summary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import gov.cdc.nbs.patient.profile.summary.PatientSummary.PatientSummaryIdentification;
import gov.cdc.nbs.support.TestActive;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PatientSummaryIdentificationSteps {

    @Autowired
    private TestActive<PatientSummary> summary;

    @Autowired
    private PatientSummaryIdentificationResolver summaryIdentificationResolver;

    private List<PatientSummaryIdentification> summaryIdentifications;
    private Exception exception;

    @When("patient summary identifications are requested by patient summary")
    public void a_patient_summary_identification_is_requested_by_patient_identifier() {
        try {
            summaryIdentifications = summaryIdentificationResolver.resolve(summary.maybeActive().orElse(null));
        } catch (Exception e) {
            this.exception = e;
        }
    }

    @Then("the patient summary identifications are found")
    public void the_summary_identifications_are_found() {
        assertNull(exception);
        assertNotNull(summaryIdentifications);
        assertFalse(summaryIdentifications.isEmpty());
    }

    @Then("the patient summary identifications are not accessible")
    public void the_summary_identification_are_not_accessible() {
        assertThat(summaryIdentifications).isNull();
        assertThat(this.exception).isInstanceOf(AccessDeniedException.class);
    }

}

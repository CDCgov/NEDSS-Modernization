package gov.cdc.nbs.patient.profile.history;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class PatientPhoneLocatorHistorySteps {
    private final Active<PatientIdentifier> activePatient;
    private final PatientPhoneLocatorHistoryPreviousVersionVerifier versionVerifier;
    private final JdbcTemplate template;

    public PatientPhoneLocatorHistorySteps(Active<PatientIdentifier> activePatient, PatientPhoneLocatorHistoryPreviousVersionVerifier versionVerifier, JdbcTemplate template) {
        this.activePatient = activePatient;
        this.versionVerifier = versionVerifier;
        this.template = template;
    }

    @Then("the patient phone locator history contains the previous version")
    public void the_patient_phone_locator_history_contains_the_previous_version() {
        long entityUid = this.activePatient.maybeActive().get().id();
        boolean verified = this.versionVerifier.verify(getLocatorUid(entityUid));

        assertThat(verified).isTrue();
    }

    private long getLocatorUid(long entityUid) throws NullPointerException {
        String QUERY = "SELECT TOP 1 locator_uid FROM Entity_locator_participation WHERE entity_uid = ?";
        return template.queryForObject(QUERY, Long.class, entityUid);
    }
}

package gov.cdc.nbs.patient.profile.history;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class PatientPostalLocatorHistorySteps {

  private static final String QUERY = """
      SELECT TOP 1 locator_uid FROM Entity_locator_participation WHERE entity_uid = ?
      """;

  private final Active<PatientIdentifier> activePatient;
  private final PatientPostalLocatorHistoryPreviousVersionVerifier versionVerifier;
  private final JdbcTemplate template;

  PatientPostalLocatorHistorySteps(
      final Active<PatientIdentifier> activePatient,
      final PatientPostalLocatorHistoryPreviousVersionVerifier versionVerifier,
      final JdbcTemplate template
  ) {
    this.activePatient = activePatient;
    this.versionVerifier = versionVerifier;
    this.template = template;
  }

  @Then("the patient postal locator history contains the previous version")
  public void the_patient_postal_locator_history_contains_the_previous_version() {
    long entityUid = this.activePatient.maybeActive().get().id();
    boolean verified = this.versionVerifier.verify(getLocatorUid(entityUid));

    assertThat(verified).isTrue();
  }

  private long getLocatorUid(long entityUid) {
    return template.query(
        QUERY,
        statement -> statement.setLong(1, entityUid),
        (resultSet, row) -> resultSet.getLong(1)
    ).stream().findFirst().orElse(1L);
  }
}

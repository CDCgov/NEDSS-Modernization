package gov.cdc.nbs.patient.profile.race;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.profile.PatientProfile;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientProfileRaceSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientRaceResolver resolver;

  private final PatientProfileRaceRequester requester;
  private final Active<ResultActions> response;

  PatientProfileRaceSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientRaceResolver resolver,
      final PatientProfileRaceRequester requester,
      final Active<ResultActions> response
  ) {
    this.activePatient = activePatient;
    this.resolver = resolver;
    this.requester = requester;
    this.response = response;
  }



  @Given("I view the Patient Profile Races")
  public void i_view_the_patient_profile_races() {
    this.activePatient.maybeActive()
        .map(this.requester::races)
        .ifPresent(this.response::active);
  }

  @Then("the profile has associated races")
  public void the_profile_has_associated_races() {
    long patient = this.activePatient.active().id();

    PatientProfile profile = new PatientProfile(patient, "local", (short) 1, RecordStatus.ACTIVE.toString());

    GraphQLPage page = new GraphQLPage(1);

    Page<PatientRace> actual = this.resolver.resolve(profile, page);
    assertThat(actual).isNotEmpty();
  }

  @Then("the profile has no associated races")
  public void the_profile_has_no_associated_races() {
    long patient = this.activePatient.active().id();

    PatientProfile profile = new PatientProfile(patient, "local", (short) 1);

    GraphQLPage page = new GraphQLPage(1);

    Page<PatientRace> actual = this.resolver.resolve(profile, page);
    assertThat(actual).isEmpty();
  }

  @Then("the patient's race includes the category {raceCategory}")
  public void the_patients_race_includes_the_category(final String category) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findPatientProfile.races.content[*].category.id")
            .value(hasItem(equalTo(category))));
  }

  @Then("the patient's race does not include the category {raceCategory}")
  public void the_patients_race_does_not_include_the_category(final String category) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findPatientProfile.races.content[*].category.id")
            .value(not(hasItem(equalTo(category)))));
  }

  @Then("the patient's race as of {localDate} includes the category {raceCategory}")
  public void the_patients_race_as_of_includes_the_category(final LocalDate asOf, final String category)
      throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findPatientProfile.races.content[?(@.category.id=='%s')].asOf", category)
            .value(hasItem(equalTo(asOf.toString()))));
  }

  @Then("the patient's race includes {raceDetail} within the category {raceCategory}")
  public void the_patients_race_includes_the_detail(final String detail, final String category) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.data.findPatientProfile.races.content[?(@.category.id=='%s')].detailed[*].id",
                category
            ).value(hasItem(equalTo(detail)))
        );
  }

  @Then("the patient's race does not include {raceDetail} within the category {raceCategory}")
  public void the_patients_race_does_not_include_the_detail(final String detail, final String category)
      throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.data.findPatientProfile.races.content[?(@.category.id=='%s')].detailed[?(@.id=='%s')]",
                category,
                detail
            ).doesNotExist()
        );
  }
}

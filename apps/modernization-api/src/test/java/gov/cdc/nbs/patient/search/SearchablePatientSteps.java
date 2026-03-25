package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.search.indexing.SearchablePatientMother;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.Given;

public class SearchablePatientSteps {

  private final Available<PatientIdentifier> patients;
  private final SearchablePatientMother mother;
  private final Available<SearchablePatient> availableSearchablePatient;
  private final Active<SearchablePatient> activeSearchablePatient;

  SearchablePatientSteps(
      final Available<PatientIdentifier> patients,
      final SearchablePatientMother mother,
      final Available<SearchablePatient> availableSearchablePatient,
      final Active<SearchablePatient> activeSearchablePatient) {
    this.patients = patients;
    this.mother = mother;
    this.availableSearchablePatient = availableSearchablePatient;
    this.activeSearchablePatient = activeSearchablePatient;
  }

  @Given("there are {int} patients available for search")
  public void there_are_patients(int patientCount) {

    for (int i = 0; i < patientCount; i++) {
      this.mother.searchable();
    }

    this.mother.searchable(this.patients.all());
  }

  @Given("I am looking for one of them")
  public void I_am_looking_for_one_of_them() {
    this.availableSearchablePatient.random().ifPresent(this.activeSearchablePatient::active);
  }

  @Given("patients are available for search")
  public void patients_are_available_for_search() {
    //  make all patients searchable
    this.mother.searchable(this.patients.all());
  }
}

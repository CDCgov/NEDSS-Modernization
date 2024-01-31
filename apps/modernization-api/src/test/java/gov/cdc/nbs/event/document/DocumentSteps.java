package gov.cdc.nbs.event.document;

import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.document.DocumentMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;

public class DocumentSteps {

  private final Active<PatientIdentifier> patient;
  private final DocumentMother documentMother;
  private final PatientMother patientMother;

  public DocumentSteps(
      final Active<PatientIdentifier> patient,
      final DocumentMother documentMother,
      final PatientMother patientMother
  ) {
    this.patient = patient;
    this.documentMother = documentMother;
    this.patientMother = patientMother;
  }

  @ParameterType(name = "documentType", value = "lab report|Morbidity Report|case report|document")
  public String documentType(final String value) {
    return switch (value.toLowerCase()) {
      case "lab report" -> "Laboratory Report";
      case "morbidity report" -> "Morbidity Report";
      case "document" -> "Case Report";
      default -> value;
    };
  }

  @Given("the patient has an unprocessed document")
  public void the_patient_has_an_unprocessed_document() {
    PatientIdentifier revision = patientMother.revise(patient.active());
    documentMother.unprocessed(revision.id());
  }
}

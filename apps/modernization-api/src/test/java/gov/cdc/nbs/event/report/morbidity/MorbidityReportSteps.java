package gov.cdc.nbs.event.report.morbidity;

import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.morbidity.MorbidityReportMother;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

public class MorbidityReportSteps {
  private static final long PIEDMONT_HOSPITAL = 10003001L;
  private final Active<PatientIdentifier> patient;
  private final MorbidityReportMother reportMother;

  public MorbidityReportSteps(
      final Active<PatientIdentifier> patient,
      final MorbidityReportMother reportMother
  ) {
    this.patient = patient;
    this.reportMother = reportMother;
  }

  @Given("the patient has an unprocessed morbidity report")
  public void patient_has_an_unprocessed_morbidity_report() {
    patient.maybeActive()
        .ifPresent(
            existing -> reportMother.unprocessed(
                existing.id(),
                PIEDMONT_HOSPITAL
            )
        );
  }
}

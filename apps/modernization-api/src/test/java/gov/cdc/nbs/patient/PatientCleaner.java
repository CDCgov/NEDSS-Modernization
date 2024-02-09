package gov.cdc.nbs.patient;

import gov.cdc.nbs.identity.MotherSettings;
import org.springframework.stereotype.Component;

@Component
class PatientCleaner {

  private final MotherSettings settings;
  private final TestPatientCleaner patientCleaner;
  private final PatientHistoryCleaner historyCleaner;

  PatientCleaner(
      final MotherSettings settings,
      final TestPatientCleaner patientCleaner,
      final PatientHistoryCleaner historyCleaner
  ) {
    this.settings = settings;
    this.patientCleaner = patientCleaner;
    this.historyCleaner = historyCleaner;
  }

  void clean() {
    this.historyCleaner.clean();
    this.patientCleaner.clean(settings.starting());
  }
}

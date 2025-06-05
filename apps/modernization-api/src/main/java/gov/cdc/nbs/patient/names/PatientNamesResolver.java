package gov.cdc.nbs.patient.names;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
class PatientNamesResolver {
  private final PatientNamesFinder patientNamesFinder;

  PatientNamesResolver(
      final PatientNamesFinder patientNamesFinder) {
    this.patientNamesFinder = patientNamesFinder;
  }

  public List<PatientName> resolve(final long patientId) {
    return patientNamesFinder.find(patientId);
  }
}

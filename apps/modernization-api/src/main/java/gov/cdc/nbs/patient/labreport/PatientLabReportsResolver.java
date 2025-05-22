package gov.cdc.nbs.patient.labreport;

import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
class PatientLabReportsResolver {
  private static final Permission PERMISSION = new Permission("view", "ObservationLabReport");

  private final PatientLabReportsFinder patientLabReportsFinder;
  private final PatientTestResultsFinder patientTestResultsFinder;
  private final PermissionScopeResolver scopeResolver;

  PatientLabReportsResolver(
      final PatientLabReportsFinder patientLabReportsFinder,
      final PatientTestResultsFinder patientTestResultsFinder,
      final PermissionScopeResolver scopeResolver) {
    this.patientLabReportsFinder = patientLabReportsFinder;
    this.patientTestResultsFinder = patientTestResultsFinder;
    this.scopeResolver = scopeResolver;
  }

  public List<PatientLabReport> resolve(final long patientId) {
    PermissionScope scope = this.scopeResolver.resolve(PERMISSION);
    List<PatientLabReport> labReportResults = patientLabReportsFinder.find(patientId, scope.any());
    List<PatientLabReport.TestResult> patientTestResults = patientTestResultsFinder.find(patientId);
    for (int i = 0; i < labReportResults.size(); ++i) {
      PatientLabReport patientLabReport = labReportResults.get(i);
      for (int j = 0; j < patientTestResults.size(); ++j) {
        PatientLabReport.TestResult testResult = patientTestResults.get(j);
        if (testResult.observationUid() == patientLabReport.labIdentifier()) {
          patientLabReport.testResults().add(testResult);
        }
      }
    }
    return labReportResults;
  }
}

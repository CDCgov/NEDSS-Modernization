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
  private final PermissionScopeResolver scopeResolver;

  PatientLabReportsResolver(
      final PatientLabReportsFinder patientLabReportsFinder,
      final PermissionScopeResolver scopeResolver) {
    this.patientLabReportsFinder = patientLabReportsFinder;
    this.scopeResolver = scopeResolver;
  }

  public List<PatientLabReport> resolve(final long patientId) {
    PermissionScope scope = this.scopeResolver.resolve(PERMISSION);
    List<PatientLabReport> labReportResults = patientLabReportsFinder.find(patientId, scope.any());
    return labReportResults;
  }
}

package gov.cdc.nbs.patient.names;

import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
class PatientNamesResolver {
  private final PatientNamesFinder patientNamesFinder;

  PatientNamesResolver(
      final PatientNamesFinder patientNamesFinder,
      final PermissionScopeResolver scopeResolver) {
    this.patientNamesFinder = patientNamesFinder;
  }

  public List<PatientName> resolve(final long patientId) {
    List<PatientName> labReportResults = patientNamesFinder.find(patientId);
    return labReportResults;
  }
}

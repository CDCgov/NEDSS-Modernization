package gov.cdc.nbs.patient.labreport;

import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import gov.cdc.nbs.patient.events.tests.ResultedTest;
import gov.cdc.nbs.patient.events.tests.ResultedTestResolver;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
class PatientLabReportsResolver {
  private static final Permission PERMISSION = new Permission("view", "ObservationLabReport");

  private final PatientLabReportsFinder patientLabReportsFinder;
  private final ResultedTestResolver resultedTestResolver;
  private final PermissionScopeResolver scopeResolver;

  PatientLabReportsResolver(
      final PatientLabReportsFinder patientLabReportsFinder,
      final ResultedTestResolver resultedTestResolver,
      final PermissionScopeResolver scopeResolver) {
    this.patientLabReportsFinder = patientLabReportsFinder;
    this.resultedTestResolver = resultedTestResolver;
    this.scopeResolver = scopeResolver;
  }

  public List<PatientLabReport> resolve(final long patientId) {
    PermissionScope scope = this.scopeResolver.resolve(PERMISSION);
    List<PatientLabReport> labReportResults = patientLabReportsFinder.find(patientId, scope.any());
    for (PatientLabReport patientLabReport : labReportResults) {
      Map<Long, Collection<ResultedTest>> labTestResults = resultedTestResolver.resolve(List.of(patientLabReport.id()));

      patientLabReport.testResults()
          .addAll(labTestResults.getOrDefault(patientLabReport.id(), Collections.emptyList()));
    }
    return labReportResults;
  }
}

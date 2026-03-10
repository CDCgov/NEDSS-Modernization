package gov.cdc.nbs.patient.file.events.report.laboratory;

import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import gov.cdc.nbs.patient.events.investigation.association.AssociatedInvestigation;
import gov.cdc.nbs.patient.events.investigation.association.AssociatedInvestigationFinder;
import gov.cdc.nbs.patient.events.tests.ResultedTest;
import gov.cdc.nbs.patient.events.tests.ResultedTestResolver;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
class PatientLabReportsResolver {
  private static final Permission PERMISSION = new Permission("view", "ObservationLabReport");
  private static final Permission ASSOCIATION = new Permission("view", "Investigation");

  private final PermissionScopeResolver scopeResolver;
  private final PatientLabReportsFinder finder;
  private final ResultedTestResolver resultedTestResolver;
  private final AssociatedInvestigationFinder associatedInvestigationFinder;

  PatientLabReportsResolver(
      final PermissionScopeResolver scopeResolver,
      final PatientLabReportsFinder finder,
      final ResultedTestResolver resultedTestResolver,
      final AssociatedInvestigationFinder associatedInvestigationFinder) {
    this.scopeResolver = scopeResolver;
    this.finder = finder;
    this.resultedTestResolver = resultedTestResolver;
    this.associatedInvestigationFinder = associatedInvestigationFinder;
  }

  List<PatientLabReport> resolve(final long patient) {
    PermissionScope scope = this.scopeResolver.resolve(PERMISSION);
    PermissionScope associationScope = this.scopeResolver.resolve(ASSOCIATION);

    if (scope.allowed()) {

      List<PatientLabReport> reports = finder.find(patient, scope);

      if (!reports.isEmpty()) {

        List<Long> identifiers = reports.stream().map(PatientLabReport::id).toList();

        Map<Long, Collection<ResultedTest>> resultedTests =
            resultedTestResolver.resolve(identifiers);

        Map<Long, Collection<AssociatedInvestigation>> associations =
            associatedInvestigationFinder.find(identifiers, associationScope);

        if (resultedTests.isEmpty() && associations.isEmpty()) {
          return reports;
        }

        return reports.stream()
            .map(
                report ->
                    report
                        .withResultedTests(
                            resultedTests.getOrDefault(report.id(), Collections.emptyList()))
                        .withAssociations(
                            associations.getOrDefault(report.id(), Collections.emptyList())))
            .toList();
      }
    }
    return Collections.emptyList();
  }
}

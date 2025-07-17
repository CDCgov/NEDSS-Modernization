package gov.cdc.nbs.patient.file.events.report.morbidity;

import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import gov.cdc.nbs.patient.events.investigation.association.AssociatedInvestigation;
import gov.cdc.nbs.patient.events.investigation.association.AssociatedInvestigationFinder;
import gov.cdc.nbs.patient.events.report.morbidity.MorbidityReportResultedTestResolver;
import gov.cdc.nbs.patient.events.report.morbidity.MorbidityReportTreatmentFinder;
import gov.cdc.nbs.patient.events.tests.ResultedTest;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
class PatientMorbidityReportResolver {

  private static final Permission PERMISSION = new Permission("view", "ObservationMorbidityReport");
  private static final Permission TREATMENT_PERMISSION = new Permission("View", "Treatment");
  private static final Permission ASSOCIATION = new Permission("view", "Investigation");

  private final PermissionScopeResolver scopeResolver;
  private final PatientMorbidityReportFinder finder;
  private final MorbidityReportTreatmentFinder treatmentFinder;
  private final MorbidityReportResultedTestResolver resultedTestResolver;
  private final AssociatedInvestigationFinder associatedInvestigationFinder;

  PatientMorbidityReportResolver(
      final PermissionScopeResolver scopeResolver,
      final PatientMorbidityReportFinder finder,
      final MorbidityReportTreatmentFinder treatmentFinder,
      final MorbidityReportResultedTestResolver resultedTestResolver,
      final AssociatedInvestigationFinder associatedInvestigationFinder
  ) {
    this.scopeResolver = scopeResolver;
    this.finder = finder;
    this.treatmentFinder = treatmentFinder;
    this.resultedTestResolver = resultedTestResolver;
    this.associatedInvestigationFinder = associatedInvestigationFinder;
  }

  List<PatientMorbidityReport> resolve(final long patient) {
    PermissionScope scope = this.scopeResolver.resolve(PERMISSION);
    PermissionScope associationScope = this.scopeResolver.resolve(ASSOCIATION);
    PermissionScope treatmentScope = this.scopeResolver.resolve(TREATMENT_PERMISSION);

    if (scope.allowed()) {

      List<PatientMorbidityReport> reports = finder.find(patient, scope);

      if (!reports.isEmpty()) {

        List<Long> identifiers = reports.stream().map(PatientMorbidityReport::id).toList();

        Map<Long, Collection<String>> treatments = treatments(identifiers, treatmentScope);

        Map<Long, Collection<ResultedTest>> resultedTests = resultedTestResolver.resolve(identifiers);

        Map<Long, Collection<AssociatedInvestigation>> associations =
            associatedInvestigationFinder.find(identifiers, associationScope);

        if (treatments.isEmpty() && resultedTests.isEmpty() && associations.isEmpty()) {
          return reports;
        }

        return reports.stream()
            .map(report -> report.withTreatments(treatments.getOrDefault(report.id(), Collections.emptyList()))
                .withResultedTests(resultedTests.getOrDefault(report.id(), Collections.emptyList()))
                .withAssociations(associations.getOrDefault(report.id(), Collections.emptyList()))
            )
            .toList();
      }

    }

    return Collections.emptyList();
  }

  private Map<Long, Collection<String>> treatments(
      final Collection<Long> identifiers,
      final PermissionScope scope
  ) {
    return scope.allowed()
        ? this.treatmentFinder.find(identifiers)
        : Collections.emptyMap();
  }
}

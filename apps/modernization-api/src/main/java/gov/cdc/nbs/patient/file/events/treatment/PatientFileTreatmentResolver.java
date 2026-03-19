package gov.cdc.nbs.patient.file.events.treatment;

import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import gov.cdc.nbs.patient.events.investigation.association.AssociatedInvestigation;
import gov.cdc.nbs.patient.events.investigation.association.AssociatedInvestigationFinder;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
class PatientFileTreatmentResolver {

  private static final Permission ASSOCIATION = new Permission("view", "Investigation");

  private final PermissionScopeResolver scopeResolver;
  private final PatientFileTreatmentFinder finder;
  private final AssociatedInvestigationFinder associatedInvestigationFinder;

  PatientFileTreatmentResolver(
      final PermissionScopeResolver scopeResolver,
      final PatientFileTreatmentFinder finder,
      final AssociatedInvestigationFinder associatedInvestigationFinder) {
    this.scopeResolver = scopeResolver;
    this.finder = finder;
    this.associatedInvestigationFinder = associatedInvestigationFinder;
  }

  List<PatientFileTreatment> resolve(final long patient) {

    PermissionScope associationScope = this.scopeResolver.resolve(ASSOCIATION);

    List<PatientFileTreatment> treatments = finder.find(patient);

    if (!treatments.isEmpty()) {

      List<Long> identifiers = treatments.stream().map(PatientFileTreatment::id).toList();

      Map<Long, Collection<AssociatedInvestigation>> associations =
          associatedInvestigationFinder.find(identifiers, associationScope);

      if (associations.isEmpty()) {
        return treatments;
      }

      return treatments.stream()
          .map(
              treatment ->
                  treatment.withAssociations(
                      associations.getOrDefault(treatment.id(), Collections.emptyList())))
          .toList();
    }

    return Collections.emptyList();
  }
}

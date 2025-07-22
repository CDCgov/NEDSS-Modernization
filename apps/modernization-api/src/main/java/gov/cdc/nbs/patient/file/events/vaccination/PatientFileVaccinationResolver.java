package gov.cdc.nbs.patient.file.events.vaccination;

import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import gov.cdc.nbs.patient.events.investigation.association.AssociatedInvestigation;
import gov.cdc.nbs.patient.events.investigation.association.AssociatedInvestigationFinder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
class PatientFileVaccinationResolver {

  private static final Permission ASSOCIATION = new Permission("view", "Investigation");

  private final PermissionScopeResolver scopeResolver;
  private final PatientFileVaccinationFinder finder;
  private final AssociatedInvestigationFinder associatedInvestigationFinder;

  PatientFileVaccinationResolver(
      final PermissionScopeResolver scopeResolver,
      final PatientFileVaccinationFinder finder,
      final AssociatedInvestigationFinder associatedInvestigationFinder
  ) {
    this.scopeResolver = scopeResolver;
    this.finder = finder;
    this.associatedInvestigationFinder = associatedInvestigationFinder;
  }

  List<PatientVaccination> resolve(final long patient) {

    PermissionScope associationScope = this.scopeResolver.resolve(ASSOCIATION);

      List<PatientVaccination> vaccinations = finder.find(patient);

      if (!vaccinations.isEmpty()) {

        List<Long> identifiers = vaccinations.stream().map(PatientVaccination::id).toList();

        Map<Long, Collection<AssociatedInvestigation>> associations =
            associatedInvestigationFinder.find(identifiers, associationScope);

        if (associations.isEmpty()) {
          return vaccinations;
        }

        return vaccinations.stream()
            .map(vaccination -> vaccination.withAssociations(associations.getOrDefault(vaccination.id(), Collections.emptyList())))
            .toList();
      }


    return Collections.emptyList();
  }
}

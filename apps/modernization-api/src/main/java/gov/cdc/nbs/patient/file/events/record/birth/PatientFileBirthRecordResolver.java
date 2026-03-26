package gov.cdc.nbs.patient.file.events.record.birth;

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
class PatientFileBirthRecordResolver {

  private static final Permission ASSOCIATION = new Permission("view", "Investigation");

  private final PermissionScopeResolver scopeResolver;
  private final PatientFileBirthRecordFinder finder;
  private final AssociatedInvestigationFinder associatedInvestigationFinder;

  PatientFileBirthRecordResolver(
      final PermissionScopeResolver scopeResolver,
      final PatientFileBirthRecordFinder finder,
      final AssociatedInvestigationFinder associatedInvestigationFinder) {
    this.scopeResolver = scopeResolver;
    this.finder = finder;
    this.associatedInvestigationFinder = associatedInvestigationFinder;
  }

  Collection<PatientFileBirthRecord> resolve(final long patient) {

    PermissionScope associationScope = this.scopeResolver.resolve(ASSOCIATION);

    Collection<PatientFileBirthRecord> records = finder.find(patient);

    if (!records.isEmpty()) {

      List<Long> identifiers = records.stream().map(PatientFileBirthRecord::id).toList();

      Map<Long, Collection<AssociatedInvestigation>> associations =
          associatedInvestigationFinder.find(identifiers, associationScope);

      if (associations.isEmpty()) {
        return records;
      }

      return records.stream()
          .map(
              treatment ->
                  treatment.withAssociations(
                      associations.getOrDefault(treatment.id(), Collections.emptyList())))
          .toList();
    }

    return Collections.emptyList();
  }
}

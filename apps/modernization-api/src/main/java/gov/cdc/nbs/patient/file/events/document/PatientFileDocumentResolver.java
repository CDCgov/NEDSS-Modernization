package gov.cdc.nbs.patient.file.events.document;

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
class PatientFileDocumentResolver {

  private static final Permission PERMISSION = new Permission("view", "document");
  private static final Permission ASSOCIATION = new Permission("view", "Investigation");

  private final PermissionScopeResolver resolver;
  private final PatientFileDocumentFinder finder;
  private final AssociatedInvestigationFinder associationFinder;

  PatientFileDocumentResolver(
      final PermissionScopeResolver resolver,
      final PatientFileDocumentFinder finder,
      final AssociatedInvestigationFinder associationFinder) {
    this.resolver = resolver;
    this.finder = finder;
    this.associationFinder = associationFinder;
  }

  List<PatientFileDocument> resolve(final long patient) {
    PermissionScope scope = this.resolver.resolve(PERMISSION);
    PermissionScope associationScope = this.resolver.resolve(ASSOCIATION);

    List<PatientFileDocument> documents = finder.find(patient, scope);

    if (!documents.isEmpty()) {
      List<Long> identifiers = documents.stream().map(PatientFileDocument::id).toList();

      Map<Long, Collection<AssociatedInvestigation>> associations =
          associationFinder.find(identifiers, associationScope);

      if (associations.isEmpty()) {
        return documents;
      }

      return documents.stream()
          .map(
              treatment ->
                  treatment.withAssociations(
                      associations.getOrDefault(treatment.id(), Collections.emptyList())))
          .toList();
    }

    return Collections.emptyList();
  }
}

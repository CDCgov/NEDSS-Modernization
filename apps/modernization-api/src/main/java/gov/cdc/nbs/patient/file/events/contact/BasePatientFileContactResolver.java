package gov.cdc.nbs.patient.file.events.contact;

import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;

import java.util.List;

class BasePatientFileContactResolver {

  private static final Permission SCOPE = new Permission("view", "ct_contact");
  private static final Permission ASSOCIATION = new Permission("view", "Investigation");

  private final PermissionScopeResolver resolver;
  private final PatientFileContactFinder finder;

  BasePatientFileContactResolver(
      final PermissionScopeResolver resolver,
      final PatientFileContactFinder finder
  ) {
    this.resolver = resolver;
    this.finder = finder;
  }

  List<PatientFileContacts> resolve(final long patient) {
    PermissionScope scope = resolver.resolve(SCOPE);
    PermissionScope associatedScope = resolver.resolve(ASSOCIATION);

    return finder.find(patient, scope, associatedScope);

  }

}

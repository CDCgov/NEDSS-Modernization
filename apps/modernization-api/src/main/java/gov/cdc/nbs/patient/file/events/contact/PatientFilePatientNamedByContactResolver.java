package gov.cdc.nbs.patient.file.events.contact;

import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import org.springframework.stereotype.Component;

@Component
class PatientFilePatientNamedByContactResolver extends BasePatientFileContactResolver {

  PatientFilePatientNamedByContactResolver(
      final PermissionScopeResolver resolver,
      final PatientFilePatientNamedByContactFinder finder
  ) {
    super(resolver, finder);
  }


}

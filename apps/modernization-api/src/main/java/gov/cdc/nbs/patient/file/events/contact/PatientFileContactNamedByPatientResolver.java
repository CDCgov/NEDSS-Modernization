package gov.cdc.nbs.patient.file.events.contact;

import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import org.springframework.stereotype.Component;

@Component
class PatientFileContactNamedByPatientResolver extends BasePatientFileContactResolver {


  PatientFileContactNamedByPatientResolver(
      final PermissionScopeResolver resolver,
      final PatientFileContactNamedByPatientFinder finder
  ) {
    super(resolver, finder);
  }


}

package gov.cdc.nbs.patient.profile.general;

import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
class PatientHIVCaseResolver {

  private static final Permission HIV_PERMISSION = new Permission("HIVQuestions", "Global");

  private final PermissionScopeResolver resolver;

  PatientHIVCaseResolver(final PermissionScopeResolver resolver) {
    this.resolver = resolver;
  }

  @SchemaMapping(field = "stateHIVcase", typeName = "PatientGeneral")
  @PreAuthorize("hasAuthority('FIND-PATIENT')")
  Optional<String> resolve(final PatientGeneral patient) {

    PermissionScope scope = resolver.resolve(HIV_PERMISSION);

    return scope.allowed()
        ? Optional.ofNullable(patient.stateHIVCase())
        : Optional.empty();
  }

}

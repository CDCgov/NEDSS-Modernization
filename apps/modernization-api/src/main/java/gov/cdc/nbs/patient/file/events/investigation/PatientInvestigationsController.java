package gov.cdc.nbs.patient.file.events.investigation;

import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('VIEW-INVESTIGATION')")
@RequestMapping("/nbs/api/patient/{patientId}/investigations")
class PatientInvestigationsController {
  private static final Permission PERMISSION = new Permission("view", "investigation");

  private final PatientInvestigationsFinder finder;
  private final PermissionScopeResolver scopeResolver;

  PatientInvestigationsController(
      final PermissionScopeResolver scopeResolver,
      final PatientInvestigationsFinder finder) {
    this.finder = finder;
    this.scopeResolver = scopeResolver;
  }

  @Operation(
      summary = "Patient Investigations",
      description = "Patient Investigations",
      tags = "PatientFile"
  )
  @GetMapping
  List<PatientInvestigation> investigations(@PathVariable final long patientId) {
    PermissionScope scope = this.scopeResolver.resolve(PERMISSION);
    return finder.findAll(patientId, scope);
  }

  @Operation(
      summary = "Patient Open Investigations",
      description = "Patient Open Investigations",
      tags = "PatientFile"
  )
  @GetMapping("/open")
  List<PatientInvestigation> openInvestigations(@PathVariable final long patientId) {
    PermissionScope scope = this.scopeResolver.resolve(PERMISSION);
    return finder.findOpen(patientId, scope);
  }
}

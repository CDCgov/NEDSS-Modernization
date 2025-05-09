package gov.cdc.nbs.patient.investigation;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;

@RestController
public class PatientInvestigationsController {
  private static final Permission PERMISSION = new Permission("view", "investigation");

  private final PatientInvestigationsFinder finder;
  private final PermissionScopeResolver scopeResolver;

  PatientInvestigationsController(
      final PermissionScopeResolver scopeResolver,
      final PatientInvestigationsFinder finder) {
    this.finder = finder;
    this.scopeResolver = scopeResolver;
  }

  @PreAuthorize("hasAuthority('VIEW-INVESTIGATION')")
  @Operation(operationId = "patientInvestigations", summary = "Patient Investigations",
      description = "Patient Investigations",
      tags = "PatientInvestigations")
  @GetMapping("/nbs/api/patient/{patientId}/investigations")
  public List<PatientInvestigation> find(@PathVariable final long patientId) {
    PermissionScope scope = this.scopeResolver.resolve(PERMISSION);
    return finder.findAll(patientId, scope.any());
  }

  @PreAuthorize("hasAuthority('VIEW-INVESTIGATION')")
  @Operation(operationId = "patientOpenInvestigations", summary = "Patient Open Investigations",
      description = "Patient Open Investigations",
      tags = "PatientOpenInvestigations")
  @GetMapping("/nbs/api/patient/{patientId}/investigations/open")
  public List<PatientInvestigation> findOpen(@PathVariable final long patientId) {
    PermissionScope scope = this.scopeResolver.resolve(PERMISSION);
    return finder.findOpen(patientId, scope.any());
  }
}

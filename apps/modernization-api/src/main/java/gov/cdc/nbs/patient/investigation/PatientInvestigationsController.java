package gov.cdc.nbs.patient.investigation;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientInvestigationsController {

  private final PatientInvestigationsFinder finder;

  PatientInvestigationsController(
      final PatientInvestigationsFinder finder) {
    this.finder = finder;
  }

  @PreAuthorize("hasAuthority('VIEW-INVESTIGATION')")
  @Operation(operationId = "patientInvestigations", summary = "Patient Investigations",
      description = "Patient Investigations",
      tags = "PatientInvestigations")
  @GetMapping("/nbs/api/patient/{patientId}/investigations")
  public List<PatientInvestigation> find(@PathVariable final long patientId) {
    return finder.findAll(patientId);
  }

  @PreAuthorize("hasAuthority('VIEW-INVESTIGATION')")
  @Operation(operationId = "patientOpenInvestigations", summary = "Patient Open Investigations",
      description = "Patient Open Investigations",
      tags = "PatientOpenInvestigations")
  @GetMapping("/nbs/api/patient/{patientId}/investigations/open")
  public List<PatientInvestigation> findOpen(@PathVariable final long patientId) {
    return finder.findOpen(patientId);
  }
}

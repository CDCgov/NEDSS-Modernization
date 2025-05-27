package gov.cdc.nbs.patient.labreport;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientLabReportsController {
  private final PatientLabReportsResolver patientLabReportsResolver;

  PatientLabReportsController(
      final PatientLabReportsResolver patientLabReportsResolver) {
    this.patientLabReportsResolver = patientLabReportsResolver;
  }

  @PreAuthorize("hasAuthority('VIEW-OBSERVATIONLABREPORT')")
  @Operation(operationId = "patientLabReports", summary = "Patient Lab Reports",
      description = "Patient Lab Reports",
      tags = "PatientLabReports")
  @GetMapping("/nbs/api/patient/{patientId}/labreports")
  public List<PatientLabReport> find(@PathVariable final long patientId) {
    return patientLabReportsResolver.resolve(patientId);
  }

}

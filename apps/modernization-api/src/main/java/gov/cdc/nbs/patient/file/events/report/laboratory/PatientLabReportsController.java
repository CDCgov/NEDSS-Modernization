package gov.cdc.nbs.patient.file.events.report.laboratory;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class PatientLabReportsController {
  private final PatientLabReportsResolver resolver;

  PatientLabReportsController(final PatientLabReportsResolver resolver) {
    this.resolver = resolver;
  }

  @PreAuthorize("hasAuthority('VIEW-OBSERVATIONLABREPORT')")
  @Operation(
      operationId = "laboratoryReports",
      summary = "Patient File Laboratory Reports",
      description = "Provides Documents Requiring Review for a patient",
      tags = "PatientFile"
  )
  @GetMapping("/nbs/api/patients/{patient}/reports/laboratory")
  List<PatientLabReport> find(@PathVariable final long patient) {
    return resolver.resolve(patient);
  }

}

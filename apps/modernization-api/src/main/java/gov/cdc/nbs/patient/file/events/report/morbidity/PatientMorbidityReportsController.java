package gov.cdc.nbs.patient.file.events.report.morbidity;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class PatientMorbidityReportsController {
  private final PatientMorbidityReportResolver resolver;

  PatientMorbidityReportsController(final PatientMorbidityReportResolver resolver) {
    this.resolver = resolver;
  }

  @PreAuthorize("hasAuthority('VIEW-OBSERVATIONMORBIDITYREPORT')")
  @Operation(
      operationId = "morbidityReports",
      summary = "Patient File Morbidity Reports",
      description = "Provides Morbidity Reports for a patient",
      tags = "PatientFile"
  )
  @GetMapping("/nbs/api/patients/{patient}/reports/morbidity")
  List<PatientMorbidityReport> find(@PathVariable final long patient) {
    return resolver.resolve(patient);
  }

}

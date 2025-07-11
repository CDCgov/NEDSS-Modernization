package gov.cdc.nbs.patient.file.demographics.summary;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.LocalDate;

@RestController
class PatientDemographicsSummaryController {

  private final Clock clock;
  private final PatientDemographicsSummaryResolver resolver;

  PatientDemographicsSummaryController(
      final Clock clock,
      final PatientDemographicsSummaryResolver resolver) {
    this.clock = clock;
    this.resolver = resolver;
  }

  @Operation(summary = "Patient File Demographics Summary", description = "Provides summarized demographics of a patient", tags = "PatientFile")
  @GetMapping("/nbs/api/patients/{patient}/demographics")
  @PreAuthorize("hasAuthority('VIEWWORKUP-PATIENT')")
  ResponseEntity<PatientDemographicsSummary> summary(@PathVariable("patient") long patient) {
    return resolver.resolve(patient, LocalDate.now(clock))
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}

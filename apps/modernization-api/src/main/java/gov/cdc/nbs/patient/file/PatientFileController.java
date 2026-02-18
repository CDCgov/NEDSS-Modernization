package gov.cdc.nbs.patient.file;

import io.swagger.v3.oas.annotations.Operation;
import java.time.Clock;
import java.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PatientFileController {

  private final Clock clock;
  private final PatientFileResolver finder;

  PatientFileController(final Clock clock, final PatientFileResolver finder) {
    this.clock = clock;
    this.finder = finder;
  }

  @Operation(operationId = "file", summary = "Patient File Header", tags = "PatientFile")
  @PreAuthorize("hasAuthority('VIEWWORKUP-PATIENT')")
  @GetMapping("/nbs/api/patients/{patientId}/file")
  ResponseEntity<PatientFile> find(@PathVariable final long patientId) {
    return finder
        .resolve(patientId, LocalDate.now(clock))
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}

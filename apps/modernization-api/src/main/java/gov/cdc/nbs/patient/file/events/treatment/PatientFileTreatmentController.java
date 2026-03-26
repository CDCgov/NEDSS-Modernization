package gov.cdc.nbs.patient.file.events.treatment;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PatientFileTreatmentController {
  private final PatientFileTreatmentResolver resolver;

  PatientFileTreatmentController(final PatientFileTreatmentResolver resolver) {
    this.resolver = resolver;
  }

  @PreAuthorize("hasAuthority('VIEW-TREATMENT')")
  @Operation(
      operationId = "Treatments",
      summary = "Patient File Treatments",
      description = "Provides Treatments for a patient",
      tags = "PatientFile")
  @GetMapping("/nbs/api/patients/{patient}/treatments")
  List<PatientFileTreatment> find(@PathVariable final long patient) {
    return resolver.resolve(patient);
  }
}

package gov.cdc.nbs.patient.file.events.document;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PatientFileDocumentController {
  private final PatientFileDocumentResolver resolver;

  PatientFileDocumentController(final PatientFileDocumentResolver resolver) {
    this.resolver = resolver;
  }

  @PreAuthorize("hasAuthority('VIEW-DOCUMENT')")
  @Operation(
      operationId = "Documents",
      summary = "Patient File Documents",
      description = "Provides Documents for a patient",
      tags = "PatientFile")
  @GetMapping("/nbs/api/patients/{patient}/documents")
  List<PatientFileDocument> find(@PathVariable final long patient) {
    return resolver.resolve(patient);
  }
}

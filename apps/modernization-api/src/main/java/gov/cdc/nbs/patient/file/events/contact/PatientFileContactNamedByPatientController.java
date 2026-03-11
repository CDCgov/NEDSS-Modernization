package gov.cdc.nbs.patient.file.events.contact;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PatientFileContactNamedByPatientController {
  private final PatientFileContactNamedByPatientResolver resolver;

  PatientFileContactNamedByPatientController(
      final PatientFileContactNamedByPatientResolver resolver) {
    this.resolver = resolver;
  }

  @PreAuthorize("hasAuthority('VIEW-CT_CONTACT')")
  @Operation(
      operationId = "contactsNamedByPatient",
      summary = "Patient File Contacts named by patient",
      description = "Provides contacts that were named by a patient during an investigation",
      tags = "PatientFile")
  @GetMapping("/nbs/api/patients/{patient}/contacts")
  List<PatientFileContacts> find(@PathVariable final long patient) {
    return resolver.resolve(patient);
  }
}

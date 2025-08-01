package gov.cdc.nbs.patient.file.events.contact;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class PatientFilePatientNamedByContactController {
  private final PatientFilePatientNamedByContactResolver resolver;

  PatientFilePatientNamedByContactController(final PatientFilePatientNamedByContactResolver resolver) {
    this.resolver = resolver;
  }

  @PreAuthorize("hasAuthority('VIEW-CT_CONTACT')")
  @Operation(
      operationId = "patientNamedByContact",
      summary = "Patient File Contacts that named patient",
      description = "Provides contacts that named the patient during their investigation",
      tags = "PatientFile"
  )
  @GetMapping("/nbs/api/patients/{patient}/contacts/named")
  List<PatientFileContacts> find(@PathVariable final long patient) {
    return resolver.resolve(patient);
  }

}

package gov.cdc.nbs.patient.file.events.record.birth;

import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PatientFileBirthRecordController {
  private final PatientFileBirthRecordResolver resolver;

  PatientFileBirthRecordController(final PatientFileBirthRecordResolver resolver) {
    this.resolver = resolver;
  }

  @PreAuthorize("hasAuthority('VIEW-BIRTHRECORD')")
  @Operation(
      operationId = "BirthRecords",
      summary = "Patient File Birth records",
      description = "Provides Birth records for a patient",
      tags = "PatientFile")
  @GetMapping("/nbs/api/patients/{patient}/records/birth")
  Collection<PatientFileBirthRecord> find(@PathVariable final long patient) {
    return resolver.resolve(patient);
  }
}

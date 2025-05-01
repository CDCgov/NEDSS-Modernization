package gov.cdc.nbs.patient.file.delete;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PatientDeleteController {

  @PreAuthorize("hasAuthority('DELETE-PATIENT')")
  @DeleteMapping("/nbs/api/patients/{patient}")
  ResponseEntity<PatientDeleteResult> delete(final @PathVariable("patient") long patient) {
    return ResponseEntity.accepted().build();
  }

}

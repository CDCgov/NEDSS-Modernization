package gov.cdc.nbs.patient.file.demographics.administrative;

import gov.cdc.nbs.patient.demographics.administrative.Administrative;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PatientAdministrativeInformationController {

  private final PatientAdministrativeInformationFinder finder;

  PatientAdministrativeInformationController(final PatientAdministrativeInformationFinder finder) {
    this.finder = finder;
  }

  @Operation(
      summary = "Patient File Administrative Information",
      description = "Provides the administrative information for a patient",
      tags = "PatientFile"
  )
  @GetMapping("/nbs/api/patients/{patient}/demographics/administrative")
  @PreAuthorize("hasAuthority('VIEWWORKUP-PATIENT')")
  ResponseEntity<Administrative> administrative(@PathVariable("patient") long patient) {
    return this.finder.find(patient).map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.ok().build());
  }
}

package gov.cdc.nbs.patient.file.demographics.sexBirth;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PatientSexBirthDemographicController {

  private final PatientSexBirthDemographicFinder finder;

  PatientSexBirthDemographicController(final PatientSexBirthDemographicFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "sexBirthDemographics",
      summary = "Patient File Sex & Birth Demographics",
      description = "Provides the Sex & Birth demographics for a patient",
      tags = "PatientFile"
  )
  @GetMapping("/nbs/api/patients/{patient}/demographics/sex-birth")
  @PreAuthorize("hasAuthority('VIEW-PATIENT')")
  ResponseEntity<PatientSexBirthDemographic> sexBirth(@PathVariable("patient") long patient) {
    return this.finder.find(patient).map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.ok().build());
  }
}

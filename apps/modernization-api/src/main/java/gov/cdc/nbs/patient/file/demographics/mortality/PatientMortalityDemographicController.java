package gov.cdc.nbs.patient.file.demographics.mortality;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PatientMortalityDemographicController {

  private final PatientMortalityDemographicFinder finder;

  PatientMortalityDemographicController(final PatientMortalityDemographicFinder finder) {
    this.finder = finder;
  }

  @Operation(operationId = "mortalityDemographics", summary = "Patient File Mortality Demographics", description = "Provides the Mortality demographics for a patient", tags = "PatientFile")
  @GetMapping("/nbs/api/patients/{patient}/demographics/mortality")
  @PreAuthorize("hasAuthority('VIEWWORKUP-PATIENT')")
  ResponseEntity<PatientMortalityDemographic> mortality(@PathVariable("patient") long patient) {
    return this.finder.find(patient).map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.ok().build());
  }
}

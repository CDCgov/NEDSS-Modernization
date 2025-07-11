package gov.cdc.nbs.patient.file.demographics.ethnicity;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PatientEthnicityDemographicController {

  private final PatientEthnicityDemographicFinder finder;

  PatientEthnicityDemographicController(final PatientEthnicityDemographicFinder finder) {
    this.finder = finder;
  }

  @Operation(operationId = "ethnicityDemographics", summary = "Patient file ethnicity demographics", description = "Provides the ethnicity demographics for a patient", tags = "PatientFile")
  @GetMapping("/nbs/api/patients/{patient}/demographics/ethnicity")
  @PreAuthorize("hasAuthority('VIEWWORKUP-PATIENT')")
  ResponseEntity<PatientEthnicityDemographic> ethnicity(@PathVariable("patient") long patient) {
    return this.finder.find(patient)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.ok().build());
  }
}

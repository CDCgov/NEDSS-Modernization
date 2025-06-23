package gov.cdc.nbs.patient.file.demographics.general;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PatientGeneralInformationDemographicController {

  private final PatientGeneralInformationDemographicFinder finder;

  PatientGeneralInformationDemographicController(final PatientGeneralInformationDemographicFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "generalInformationDemographics",
      summary = "Patient File General Information Demographics",
      description = "Provides the General Information demographics for a patient",
      tags = "PatientFile"
  )
  @GetMapping("/nbs/api/patients/{patient}/demographics/general")
  @PreAuthorize("hasAuthority('VIEW-PATIENT')")
  ResponseEntity<PatientGeneralInformationDemographic> generalInformation(@PathVariable("patient") long patient) {
    return this.finder.find(patient).map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.ok().build());
  }
}

package gov.cdc.nbs.patient.file.demographics.identification;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PatientIdentificationDemographicController {

  private final PatientIdentificationDemographicFinder finder;

  PatientIdentificationDemographicController(final PatientIdentificationDemographicFinder finder) {
    this.finder = finder;
  }

  @Operation(
      summary = "Patient File Identification Demographics",
      description = "Provides the identification demographics for a patient",
      tags = "PatientFile")
  @GetMapping("/nbs/api/patients/{patient}/demographics/identifications")
  @PreAuthorize("hasAuthority('VIEWWORKUP-PATIENT')")
  List<PatientIdentificationDemographic> identifications(@PathVariable("patient") long patient) {
    return this.finder.find(patient);
  }
}

package gov.cdc.nbs.patient.file.demographics.name;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class PatientNameDemographicController {
  private final PatientNameDemographicFinder finder;

  PatientNameDemographicController(
      final PatientNameDemographicFinder finder) {
    this.finder = finder;
  }

  @PreAuthorize("hasAuthority('VIEWWORKUP-PATIENT')")
  @Operation(summary = "Patient File Name Demographics", description = "Provides the name demographics for a patient", tags = "PatientFile")
  @GetMapping("/nbs/api/patients/{patient}/demographics/names")
  public List<PatientNameDemographic> names(@PathVariable final long patient) {
    return finder.find(patient);
  }

}

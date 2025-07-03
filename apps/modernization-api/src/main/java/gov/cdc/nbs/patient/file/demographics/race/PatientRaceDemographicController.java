package gov.cdc.nbs.patient.file.demographics.race;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
class PatientRaceDemographicController {

  private final PatientRaceDemographicFinder finder;

  PatientRaceDemographicController(final PatientRaceDemographicFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "raceDemographics",
      summary = "Patient file race demographics",
      description = "Provides the race demographics for a patient",
      tags = "PatientFile"
  )
  @GetMapping("/nbs/api/patients/{patient}/demographics/races")
  @PreAuthorize("hasAuthority('VIEW-PATIENT')")
  Collection<PatientRaceDemographic> races(@PathVariable("patient") long patient) {
    return this.finder.find(patient);
  }
}

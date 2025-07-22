package gov.cdc.nbs.patient.file.events.vaccination;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class PatientVaccinationController {
  private final PatientFileVaccinationResolver resolver;

  PatientVaccinationController(final PatientFileVaccinationResolver resolver) {
    this.resolver = resolver;
  }

  @PreAuthorize("hasAuthority('VIEW-INTERVENTIONVACCINERECORD')")
  @Operation(
      operationId = "Vaccinations",
      summary = "Patient File Vaccinations",
      description = "Provides Vaccinations for a patient",
      tags = "PatientFile"
  )
  @GetMapping("/nbs/api/patients/{patient}/vaccinations")
  List<PatientVaccination> find(@PathVariable final long patient) {
    return resolver.resolve(patient);
  }

}

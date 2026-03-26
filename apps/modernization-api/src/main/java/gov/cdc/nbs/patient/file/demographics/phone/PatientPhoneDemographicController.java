package gov.cdc.nbs.patient.file.demographics.phone;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PatientPhoneDemographicController {

  private final PatientPhoneDemographicFinder finder;

  PatientPhoneDemographicController(final PatientPhoneDemographicFinder finder) {
    this.finder = finder;
  }

  @Operation(
      summary = "Patient File Phone Demographics",
      description = "Provides the phone demographics for a patient",
      tags = "PatientFile")
  @GetMapping("/nbs/api/patients/{patient}/demographics/phones")
  @PreAuthorize("hasAuthority('VIEWWORKUP-PATIENT')")
  List<PatientPhoneDemographic> phones(@PathVariable("patient") long patient) {
    return this.finder.find(patient);
  }
}

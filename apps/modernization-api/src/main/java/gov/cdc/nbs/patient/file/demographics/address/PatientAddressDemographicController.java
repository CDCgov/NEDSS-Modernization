package gov.cdc.nbs.patient.file.demographics.address;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class PatientAddressDemographicController {

  private final PatientAddressDemographicFinder finder;

  PatientAddressDemographicController(final PatientAddressDemographicFinder finder) {
    this.finder = finder;
  }

  @Operation(
      summary = "Patient File Address Demographics",
      description = "Provides the address demographics for a patient",
      tags = "PatientFile"
  )
  @GetMapping("/nbs/api/patients/{patient}/demographics/addresses")
  @PreAuthorize("hasAuthority('VIEW-PATIENT')")
  List<PatientAddressDemographic> addresses(@PathVariable("patient") long patient) {
    return this.finder.find(patient);
  }
}

package gov.cdc.nbs.patient.names;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientNamesController {
  private final PatientNamesResolver patientNamesResolver;

  PatientNamesController(
      final PatientNamesResolver patientNamesResolver) {
    this.patientNamesResolver = patientNamesResolver;
  }

  @PreAuthorize("hasAuthority('VIEW-PATIENT')")
  @Operation(operationId = "patientNames", summary = "Patient Names",
      description = "Patient Names",
      tags = "PatientNames")
  @GetMapping("/nbs/api/patient/{patientId}/names")
  public List<PatientName> find(@PathVariable final long patientId) {
    return patientNamesResolver.resolve(patientId);
  }

}

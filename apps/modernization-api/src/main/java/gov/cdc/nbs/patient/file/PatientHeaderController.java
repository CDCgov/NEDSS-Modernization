package gov.cdc.nbs.patient.file;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientHeaderController {

  private final PatientFileHeaderFinder finder;

  PatientHeaderController(
      final PatientFileHeaderFinder finder) {
    this.finder = finder;
  }

  @Operation(operationId = "patientFileHeader", summary = "Patient File Header", description = "Patient File Header",
      tags = "PatientFileHeader")
  @GetMapping("/nbs/api/patient/{patientId}/file")
  public PatientFileHeader find(@PathVariable final long patientId) {
    return finder.find(patientId);
  }
}

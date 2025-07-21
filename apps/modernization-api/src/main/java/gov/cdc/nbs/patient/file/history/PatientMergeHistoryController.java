package gov.cdc.nbs.patient.file.history;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class PatientMergeHistoryController {

  private final PatientMergeHistoryFinder finder;

  PatientMergeHistoryController(final PatientMergeHistoryFinder finder) {
    this.finder = finder;
  }

  @Operation(
      summary = "Patient File Merge History",
      description = "Provides the merge history for a patient",
      tags = "PatientFile"
  )
  @GetMapping("/nbs/api/patients/{patient}/merge/history")
  @PreAuthorize("hasAuthority('VIEWWORKUP-PATIENT')")
  ResponseEntity<List<PatientMergeHistory>> mergeHistory(@PathVariable("patient") long patient) {
    List<PatientMergeHistory> history = this.finder.find(patient);
    if (history.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(history);
  }
}

package gov.cdc.nbs.patient.file.summary.drr;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DocumentsRequiringReviewController {

  private final DocumentRequiringReviewResolver documentsResolver;

  DocumentsRequiringReviewController(final DocumentRequiringReviewResolver documentsResolver) {
    this.documentsResolver = documentsResolver;
  }

  @Operation(
      summary = "Patient File Documents Requiring Review",
      description = "Provides Documents Requiring Review for a patient",
      tags = "PatientFile")
  @PreAuthorize(
      "hasAuthority('FIND-PATIENT') and (hasAuthority('VIEW-DOCUMENT') or hasAuthority('VIEW-OBSERVATIONLABREPORT') or hasAuthority('VIEW-OBSERVATIONMORBIDITYREPORT'))")
  @GetMapping("/nbs/api/patients/{patient}/documents-requiring-review")
  ResponseEntity<List<DocumentRequiringReview>> documentsRequiringReview(
      final @PathVariable("patient") long patient) {

    List<DocumentRequiringReview> documents = this.documentsResolver.resolve(patient);
    return ResponseEntity.ok(documents);
  }
}

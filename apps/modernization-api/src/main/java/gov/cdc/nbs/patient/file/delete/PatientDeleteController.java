package gov.cdc.nbs.patient.file.delete;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.patient.PatientException;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.search.indexing.PatientIndexer;
import gov.cdc.nbs.web.response.Failure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.LocalDateTime;

import static gov.cdc.nbs.web.response.Failures.failure;

@RestController
class PatientDeleteController {

  private final Clock clock;
  private final PatientDeletionService service;

  private final PatientIndexer indexer;

  PatientDeleteController(
      final Clock clock,
      final PatientDeletionService service,
      final PatientIndexer indexer
  ) {
    this.clock = clock;
    this.service = service;
    this.indexer = indexer;
  }

  @Operation(
      operationId = "delete",
      summary = "Allows deleting of a patient.",
      tags = "PatientFile",
      responses = {
          @ApiResponse(responseCode = "202", description = "The patient has been deleted"),
          @ApiResponse(
              responseCode = "400",
              description = "The patient could not be deleted.",
              content = {
                  @Content(
                      mediaType = "application/json",
                      schema =
                      @Schema(implementation = Failure.class)

                  )
              })
      }
  )
  @PreAuthorize("hasAuthority('DELETE-PATIENT')")
  @DeleteMapping("/nbs/api/patients/{patient}")
  ResponseEntity<?> delete(
      final @PathVariable("patient") long patient,
      @Parameter(hidden = true) @AuthenticationPrincipal final NbsUserDetails user
  ) {

    RequestContext context = new RequestContext(user.getId(), LocalDateTime.now(this.clock));

    try {

      this.service.delete(context, patient);

      this.indexer.index(patient);

      return ResponseEntity.accepted().build();

    } catch (PatientException exception) {
      return failure(exception);
    }

  }



}

package gov.cdc.nbs.patient.file.edit;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.patient.PatientException;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.web.response.StandardResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.LocalDateTime;

import static gov.cdc.nbs.web.response.Failures.failure;
import static gov.cdc.nbs.web.response.Successes.accepted;

@RestController
class PatientEditController {

  private final Clock clock;
  private final PatientEditService service;

  PatientEditController(
      final Clock clock,
      final PatientEditService service
  ) {
    this.clock = clock;
    this.service = service;
  }

  @Operation(operationId = "edit", summary = "Patient File Header", tags = "PatientFile")
  @PreAuthorize("hasAuthority('EDIT-PATIENT')")
  @PutMapping("/nbs/api/patients/{patient}")
  ResponseEntity<StandardResponse> edit(
      @AuthenticationPrincipal final NbsUserDetails user,
      @RequestBody final EditedPatient changes,
      @PathVariable final long patient
  ) {
    RequestContext context = new RequestContext(user.getId(), LocalDateTime.now(this.clock));

    try {
      this.service.edit(context, patient, changes);
      return accepted();

    } catch (PatientException exception) {
      return failure(exception);
    }

  }
}

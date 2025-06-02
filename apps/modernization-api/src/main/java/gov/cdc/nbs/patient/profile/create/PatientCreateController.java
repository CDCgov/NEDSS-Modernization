package gov.cdc.nbs.patient.profile.create;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.search.indexing.PatientIndexer;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/nbs/api/profile")
@PreAuthorize("hasAuthority('ADD-PATIENT')")
public class PatientCreateController {

  private final Clock clock;
  private final PatientCreationService service;
  private final PatientIndexer indexer;

  PatientCreateController(
      final Clock clock,
      final PatientCreationService service,
      final PatientIndexer indexer
  ) {
    this.clock = clock;
    this.service = service;
    this.indexer = indexer;
  }

  @Operation(
      summary = "PatientProfile",
      description = "Allows creation of a patient",
      tags = "PatientProfile"
  )
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CreatedPatient create(@AuthenticationPrincipal NbsUserDetails user, @RequestBody final NewPatient newPatient) {
    RequestContext context = new RequestContext(user.getId(), LocalDateTime.now(this.clock));

    CreatedPatient created = service.create(context, newPatient);

    this.indexer.index(created.id());
    return created;
  }
}


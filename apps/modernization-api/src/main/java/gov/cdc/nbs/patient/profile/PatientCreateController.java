package gov.cdc.nbs.patient.profile;

import lombok.RequiredArgsConstructor;
import java.time.Clock;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.config.security.SecurityUtil;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.create.PatientCreator;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.search.indexing.PatientIndexer;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import gov.cdc.nbs.patient.profile.administrative.NewPatientAdministrative;

@Slf4j
@RestController
@RequestMapping("/nbs/api/profile")
@PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('ADD-PATIENT')")
@RequiredArgsConstructor
public class PatientCreateController {
  private final Clock clock;
  private final PatientCreator creator;
  private final PatientIndexer indexer;

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public PatientIdentifier create(@RequestBody NewPatientAdministrative adminInput) {
    var user = SecurityUtil.getUserDetails();
    RequestContext context = new RequestContext(user.getId(), Instant.now(this.clock));
    PatientInput input = new PatientInput();
    input.setComments(adminInput.comment());
    input.setAsOf(adminInput.asOf());
    PatientIdentifier created = creator.create(context, input);
    this.indexer.index(created.id());
    return created;
  }
}

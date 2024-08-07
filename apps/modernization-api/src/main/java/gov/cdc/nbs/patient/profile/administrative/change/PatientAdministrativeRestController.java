package gov.cdc.nbs.patient.profile.administrative.change;

import lombok.RequiredArgsConstructor;
import java.time.Clock;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.config.security.SecurityUtil;
import gov.cdc.nbs.patient.RequestContext;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/nbs/api/profile/admin")
@PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('EDIT-PATIENT')")
@RequiredArgsConstructor
public class PatientAdministrativeRestController {

  private final Clock clock;
  private final PatientAdministrativeChangeService service;

  @PostMapping("update")
  @ResponseStatus(HttpStatus.CREATED)
  public PatientAdministrativeChangeResult update(@RequestBody UpdatePatientAdministrative request) {
    NbsUserDetails user = SecurityUtil.getUserDetails();

    RequestContext context = new RequestContext(user.getId(), Instant.now(this.clock));

    service.update(context, request);
    return new PatientAdministrativeChangeResult(request.patient());
  }

}

package gov.cdc.nbs.patient.profile.ethnicity;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.config.security.SecurityUtil;
import gov.cdc.nbs.message.patient.input.EthnicityInput;
import gov.cdc.nbs.patient.RequestContext;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.time.Clock;
import java.time.LocalDateTime;

@Controller
class PatientEthnicityController {

  private final Clock clock;
  private final PatientEthnicityChangeService service;

  PatientEthnicityController(
      final Clock clock,
      final PatientEthnicityChangeService service
  ) {
    this.clock = clock;
    this.service = service;
  }

  @MutationMapping("updateEthnicity")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('EDIT-PATIENT')")
  public PatientEthnicityChangeResult update(@Argument EthnicityInput input) {

    NbsUserDetails user = SecurityUtil.getUserDetails();

    RequestContext context = new RequestContext(user.getId(), LocalDateTime.now(this.clock));

    service.update(context, input);

    return new PatientEthnicityChangeResult(input.getPatient());
  }
}

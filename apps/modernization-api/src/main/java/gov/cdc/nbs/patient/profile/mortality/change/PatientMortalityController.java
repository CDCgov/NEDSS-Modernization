package gov.cdc.nbs.patient.profile.mortality.change;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.config.security.SecurityUtil;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.search.indexing.PatientIndexer;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.time.Clock;
import java.time.LocalDateTime;

@Controller
class PatientMortalityController {

  private final Clock clock;
  private final PatientMortalityChangeService service;

  private final PatientIndexer indexer;

  PatientMortalityController(
      final Clock clock,
      final PatientMortalityChangeService service,
      final PatientIndexer indexer
  ) {
    this.clock = clock;
    this.service = service;
    this.indexer = indexer;
  }

  @MutationMapping("updatePatientMortality")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('EDIT-PATIENT')")
  public PatientMortalityChangeResult update(@Argument UpdatePatientMortality input) {

    NbsUserDetails user = SecurityUtil.getUserDetails();

    RequestContext context = new RequestContext(user.getId(), LocalDateTime.now(this.clock));

    service.update(context, input);

    indexer.index(input.patient());

    return new PatientMortalityChangeResult(input.patient());
  }
}

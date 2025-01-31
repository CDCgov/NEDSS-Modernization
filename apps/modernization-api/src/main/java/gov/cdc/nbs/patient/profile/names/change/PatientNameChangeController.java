package gov.cdc.nbs.patient.profile.names.change;

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
class PatientNameChangeController {

  private final Clock clock;
  private final PatientNameChangeService service;
  private final PatientIndexer indexer;

  PatientNameChangeController(
      final Clock clock,
      final PatientNameChangeService service,
      final PatientIndexer indexer
  ) {
    this.clock = clock;
    this.service = service;
    this.indexer = indexer;
  }

  @MutationMapping("addPatientName")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('EDIT-PATIENT')")
  public PatientNameChangeResult add(@Argument NewPatientNameInput input) {
    PatientNameAdded added = service.add(resolveContext(), input);
    indexer.index(input.patient());
    return new PatientNameChangeResult(added.patient(), added.sequence());
  }

  @MutationMapping("updatePatientName")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('EDIT-PATIENT')")
  public PatientNameChangeResult update(@Argument UpdatePatientNameInput input) {
    service.update(resolveContext(), input);
    return new PatientNameChangeResult(input.patient(), input.sequence());
  }

  @MutationMapping("deletePatientName")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('EDIT-PATIENT')")
  PatientNameChangeResult delete(@Argument final DeletePatientNameInput input) {
    service.delete(resolveContext(), input);
    indexer.index(input.patient());
    return new PatientNameChangeResult(input.patient(), input.sequence());
  }

  private RequestContext resolveContext() {
    NbsUserDetails user = SecurityUtil.getUserDetails();
    return new RequestContext(user.getId(), LocalDateTime.now(this.clock));
  }
}

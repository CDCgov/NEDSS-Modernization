package gov.cdc.nbs.patient.profile.phone.change;

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
class PatientPhoneChangeController {

  private final Clock clock;
  private final PatientPhoneChangeService service;
  private final PatientIndexer indexer;

  PatientPhoneChangeController(
      final Clock clock,
      final PatientPhoneChangeService service,
      final PatientIndexer indexer
  ) {
    this.clock = clock;
    this.service = service;
    this.indexer = indexer;
  }

  @MutationMapping("addPatientPhone")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('EDIT-PATIENT')")
  PatientPhoneChangeResult add(@Argument final NewPatientPhoneInput input) {
    PatientPhoneAdded added = service.add(resolveContext(), input);
    indexer.index(input.patient());
    return new PatientPhoneChangeResult(added.patient(), added.id());
  }

  @MutationMapping("updatePatientPhone")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('EDIT-PATIENT')")
  PatientPhoneChangeResult update(@Argument final UpdatePatientPhoneInput input) {
    service.update(resolveContext(), input);
    indexer.index(input.patient());
    return new PatientPhoneChangeResult(input.patient(), input.id());
  }

  @MutationMapping("deletePatientPhone")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('EDIT-PATIENT')")
  PatientPhoneChangeResult delete(@Argument final DeletePatientPhoneInput input) {
    service.delete(resolveContext(), input);
    indexer.index(input.patient());
    return new PatientPhoneChangeResult(input.patient(), input.id());
  }

  private RequestContext resolveContext() {
    NbsUserDetails user = SecurityUtil.getUserDetails();
    return new RequestContext(user.getId(), LocalDateTime.now(this.clock));
  }
}

package gov.cdc.nbs.patient.profile.address.change;

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
class PatientAddressChangeController {

  private final Clock clock;
  private final PatientAddressChangeService service;
  private final PatientIndexer indexer;

  PatientAddressChangeController(
      final Clock clock,
      final PatientAddressChangeService service,
      final PatientIndexer indexer
  ) {
    this.clock = clock;
    this.service = service;
    this.indexer = indexer;
  }

  @MutationMapping("addPatientAddress")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('EDIT-PATIENT')")
  PatientAddressChangeResult add(@Argument final NewPatientAddressInput input) {
    PatientAddressAdded added = service.add(resolveContext(), input);
    indexer.index(input.patient());
    return new PatientAddressChangeResult(added.patient(), added.id());
  }

  @MutationMapping("updatePatientAddress")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('EDIT-PATIENT')")
  PatientAddressChangeResult update(@Argument final UpdatePatientAddressInput input) {
    service.update(resolveContext(), input);
    indexer.index(input.patient());
    return new PatientAddressChangeResult(input.patient(), input.id());
  }

  @MutationMapping("deletePatientAddress")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('EDIT-PATIENT')")
  PatientAddressChangeResult delete(@Argument final DeletePatientAddressInput input) {
    service.delete(resolveContext(), input);
    indexer.index(input.patient());
    return new PatientAddressChangeResult(input.patient(), input.id());
  }

  private RequestContext resolveContext() {
    NbsUserDetails user = SecurityUtil.getUserDetails();
    return new RequestContext(user.getId(), LocalDateTime.now(this.clock));
  }
}

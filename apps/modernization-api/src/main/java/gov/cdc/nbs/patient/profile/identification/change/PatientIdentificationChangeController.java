package gov.cdc.nbs.patient.profile.identification.change;

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
class PatientIdentificationChangeController {

  private final Clock clock;
  private final PatientIdentificationChangeService service;
  private final PatientIndexer indexer;

  PatientIdentificationChangeController(
      final Clock clock,
      final PatientIdentificationChangeService service,
      final PatientIndexer indexer
  ) {
    this.clock = clock;
    this.service = service;
    this.indexer = indexer;
  }

  @MutationMapping("addPatientIdentification")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('EDIT-PATIENT')")
  PatientIdentificationChangeResult add(@Argument final NewPatientIdentificationInput input) {
    PatientIdentificationAdded added = service.add(resolveContext(), input);
    indexer.index(input.patient());
    return new PatientIdentificationChangeResult(added.patient(), added.sequence());
  }

  @MutationMapping("updatePatientIdentification")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('EDIT-PATIENT')")
  PatientIdentificationChangeResult update(@Argument final UpdatePatientIdentificationInput input) {
    service.update(resolveContext(), input);
    indexer.index(input.patient());
    return new PatientIdentificationChangeResult(input.patient(), input.sequence());
  }

  @MutationMapping("deletePatientIdentification")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('EDIT-PATIENT')")
  PatientIdentificationChangeResult delete(@Argument final DeletePatientIdentificationInput input) {
    service.delete(resolveContext(), input);
    indexer.index(input.patient());
    return new PatientIdentificationChangeResult(input.patient(), input.sequence());
  }

  private RequestContext resolveContext() {
    NbsUserDetails user = SecurityUtil.getUserDetails();
    return new RequestContext(user.getId(), LocalDateTime.now(this.clock));
  }
}

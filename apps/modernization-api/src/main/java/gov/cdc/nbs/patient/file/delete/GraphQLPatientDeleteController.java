package gov.cdc.nbs.patient.file.delete;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.config.security.SecurityUtil;
import gov.cdc.nbs.patient.PatientException;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.search.indexing.PatientIndexer;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.time.Clock;
import java.time.LocalDateTime;

@Controller
class GraphQLPatientDeleteController {

  private final Clock clock;
  private final PatientDeleter deleter;

  private final PatientIndexer indexer;

  GraphQLPatientDeleteController(
      final Clock clock,
      final PatientDeleter deleter,
      final PatientIndexer indexer
  ) {
    this.clock = clock;
    this.deleter = deleter;
    this.indexer = indexer;
  }

  @MutationMapping("deletePatient")
  @PreAuthorize("hasAuthority('VIEW-PATIENT') and hasAuthority('DELETE-PATIENT')")
  PatientDeleteResult delete(@Argument long patient) {

    NbsUserDetails user = SecurityUtil.getUserDetails();

    RequestContext context = new RequestContext(user.getId(), LocalDateTime.now(this.clock));

    try {

      this.deleter.delete(context, patient);

      this.indexer.index(patient);

      return new PatientDeleteResult.PatientDeleteSuccessful(patient);

    } catch (PatientException exception) {
      return failure(exception);
    }

  }

  private PatientDeleteResult failure(final PatientException exception) {
    return new PatientDeleteResult.PatientDeleteFailed(exception.patient(), exception.getMessage());
  }
}

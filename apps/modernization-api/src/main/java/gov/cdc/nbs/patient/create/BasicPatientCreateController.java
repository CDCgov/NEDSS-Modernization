package gov.cdc.nbs.patient.create;

import gov.cdc.nbs.config.security.SecurityUtil;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.search.indexing.PatientIndexer;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.time.Clock;
import java.time.LocalDateTime;

@Controller
class BasicPatientCreateController {

  private final Clock clock;
  private final BasicPatientCreator creator;
  private final PatientIndexer indexer;

  BasicPatientCreateController(
      final Clock clock,
      final BasicPatientCreator creator,
      final PatientIndexer indexer
  ) {
    this.clock = clock;
    this.creator = creator;
    this.indexer = indexer;
  }

  @MutationMapping("createPatient")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('ADD-PATIENT')")
  PatientIdentifier create(final @Argument("patient") PatientInput input) {

    var user = SecurityUtil.getUserDetails();

    RequestContext context = new RequestContext(user.getId(), LocalDateTime.now(this.clock));

    PatientIdentifier created = creator.create(context, input);

    this.indexer.index(created.id());

    return created;
  }

}

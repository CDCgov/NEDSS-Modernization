package gov.cdc.nbs.patient.profile.race.change;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.config.security.SecurityUtil;
import gov.cdc.nbs.message.patient.input.RaceInput;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.demographic.race.ExistingPatientRaceException;
import gov.cdc.nbs.patient.search.indexing.PatientIndexer;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.time.Clock;
import java.time.LocalDateTime;

@Controller
class PatientRaceChangeController {

  private final Clock clock;
  private final PatientRaceChangeService service;

  private final PatientIndexer indexer;

  PatientRaceChangeController(
      final Clock clock,
      final PatientRaceChangeService service,
      final PatientIndexer indexer
  ) {
    this.clock = clock;
    this.service = service;
    this.indexer = indexer;
  }

  @MutationMapping("addPatientRace")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('EDIT-PATIENT')")
  PatientRaceChangeResult add(@Argument final RaceInput input) {
    try {
      service.add(resolveContext(), input);
      indexer.index(input.getPatient());

      return new PatientRaceChangeResult.PatientRaceChangeSuccessful(input.getPatient());

    } catch (ExistingPatientRaceException exception) {
      return new PatientRaceChangeResult.PatientRaceChangeFailureExistingCategory(
          exception.patient(),
          exception.category()
      );
    }
  }

  @MutationMapping("updatePatientRace")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('EDIT-PATIENT')")
  PatientRaceChangeResult.PatientRaceChangeSuccessful update(@Argument final RaceInput input) {
    service.update(resolveContext(), input);
    indexer.index(input.getPatient());

    return new PatientRaceChangeResult.PatientRaceChangeSuccessful(input.getPatient());
  }

  @MutationMapping("deletePatientRace")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('EDIT-PATIENT')")
  PatientRaceChangeResult.PatientRaceChangeSuccessful delete(@Argument final DeletePatientRace input) {
    service.delete(resolveContext(), input);
    indexer.index(input.patient());
    return new PatientRaceChangeResult.PatientRaceChangeSuccessful(input.patient());
  }

  private RequestContext resolveContext() {
    NbsUserDetails user = SecurityUtil.getUserDetails();
    return new RequestContext(user.getId(), LocalDateTime.now(this.clock));
  }
}

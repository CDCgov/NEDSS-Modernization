package gov.cdc.nbs.patient.profile.race.change;

import gov.cdc.nbs.message.patient.input.RaceInput;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.profile.PatientProfileService;
import org.springframework.stereotype.Component;

@Component
public class PatientRaceChangeService {

  private final PatientProfileService service;

  PatientRaceChangeService(final PatientProfileService service) {
    this.service = service;
  }

  public void add(final RequestContext context, final RaceInput input) {
    this.service.using(input.getPatient(), found -> found.add(
        new PatientCommand.AddRace(
            input.getPatient(),
            input.getAsOf(),
            input.getCategory(),
            input.getDetailed(),
            context.requestedBy(),
            context.requestedAt())));
  }

  void update(final RequestContext context, final RaceInput input) {
    this.service.using(input.getPatient(), found -> found.update(
        new PatientCommand.UpdateRaceInfo(
            input.getPatient(),
            input.getAsOf(),
            input.getCategory(),
            input.getDetailed(),
            context.requestedBy(),
            context.requestedAt())));
  }

  void delete(final RequestContext context, final DeletePatientRace input) {

    this.service.using(input.patient(), found -> found.delete(
        new PatientCommand.DeleteRaceInfo(
            input.patient(),
            input.category(),
            context.requestedBy(),
            context.requestedAt())));

  }

}

package gov.cdc.nbs.patient.profile.identification.change;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientNotFoundException;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.profile.PatientProfileService;
import org.springframework.stereotype.Component;

@Component
public class PatientIdentificationChangeService {
  private final PatientProfileService service;

  PatientIdentificationChangeService(final PatientProfileService service) {
    this.service = service;
  }

  public PatientIdentificationAdded add(final RequestContext context, final NewPatientIdentificationInput input) {
    return service.with(
        input.patient(),
        found -> found.add(
            new PatientCommand.AddIdentification(
                input.patient(),
                input.asOf(),
                input.value(),
                input.authority(),
                input.type(),
                context.requestedBy(),
                context.requestedAt())))
        .map(added -> new PatientIdentificationAdded(added.getId().getEntityUid(), added.getId().getEntityIdSeq()))
        .orElseThrow(() -> new PatientNotFoundException(input.patient()));
  }

  void update(final RequestContext context, final UpdatePatientIdentificationInput input) {
    this.service.using(
        input.patient(),
        found -> found.update(
            new PatientCommand.UpdateIdentification(
                input.patient(),
                input.sequence(),
                input.asOf(),
                input.value(),
                input.authority(),
                input.type(),
                context.requestedBy(),
                context.requestedAt())));
  }

  void delete(final RequestContext context, final DeletePatientIdentificationInput input) {
    this.service.using(
        input.patient(),
        found -> found.delete(
            new PatientCommand.DeleteIdentification(
                input.patient(),
                input.sequence(),
                context.requestedBy(),
                context.requestedAt())));
  }
}

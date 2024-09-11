package gov.cdc.nbs.patient.profile.phone.change;

import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientNotFoundException;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.profile.PatientProfileService;
import org.springframework.stereotype.Component;

@Component
public class PatientPhoneChangeService {

  private final IdGeneratorService generator;
  private final PatientProfileService service;

  public PatientPhoneChangeService(
      final IdGeneratorService generator,
      final PatientProfileService service) {
    this.generator = generator;
    this.service = service;
  }

  public PatientPhoneAdded add(final RequestContext context, final NewPatientPhoneInput input) {
    return service.with(
        input.patient(),
        found -> found.add(
            new PatientCommand.AddPhone(
                input.patient(),
                generateNbsId(),
                input.type(),
                input.use(),
                input.asOf(),
                input.countryCode(),
                input.number(),
                input.extension(),
                input.email(),
                input.url(),
                input.comment(),
                context.requestedBy(),
                context.requestedAt())))
        .map(added -> new PatientPhoneAdded(input.patient(), added.getId().getLocatorUid()))
        .orElseThrow(() -> new PatientNotFoundException(input.patient()));
  }

  private Long generateNbsId() {
    var generatedId = generator.getNextValidId(IdGeneratorService.EntityType.NBS);
    return generatedId.getId();
  }

  void update(final RequestContext context, final UpdatePatientPhoneInput input) {
    this.service.using(input.patient(), found -> found.update(
        new PatientCommand.UpdatePhone(
            input.patient(),
            input.id(),
            input.type(),
            input.use(),
            input.asOf(),
            input.countryCode(),
            input.number(),
            input.extension(),
            input.email(),
            input.url(),
            input.comment(),
            context.requestedBy(),
            context.requestedAt())));

  }

  void delete(final RequestContext context, final DeletePatientPhoneInput input) {

    this.service.using(input.patient(), found -> found.delete(
        new PatientCommand.DeletePhone(
            input.patient(),
            input.id(),
            context.requestedBy(),
            context.requestedAt())));
  }
}

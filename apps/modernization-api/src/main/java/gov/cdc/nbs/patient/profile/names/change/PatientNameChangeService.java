package gov.cdc.nbs.patient.profile.names.change;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientNotFoundException;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.profile.PatientProfileService;
import org.springframework.stereotype.Component;


@Component
class PatientNameChangeService {

  private final PatientProfileService service;

  PatientNameChangeService(PatientProfileService service) {
    this.service = service;
  }

  PatientNameAdded add(final RequestContext context, final NewPatientNameInput request) {
    return service.with(
            request.patient(),
            found -> found.add(
                new PatientCommand.AddName(
                    request.patient(),
                    request.asOf(),
                    request.prefix(),
                    request.first(),
                    request.middle(),
                    request.secondMiddle(),
                    request.last(),
                    request.secondLast(),
                    request.suffix(),
                    request.degree(),
                    request.type(),
                    context.requestedBy(),
                    context.requestedAt()
                )
            )
        )
        .map(added -> new PatientNameAdded(added.getId().getPersonUid(), added.getId().getPersonNameSeq()))
        .orElseThrow(() -> new PatientNotFoundException(request.patient()));
  }

  void update(final RequestContext context, final UpdatePatientNameInput input) {
    this.service.using(input.patient(), found -> found.update(
            new PatientCommand.UpdateNameInfo(
                input.patient(),
                input.sequence(),
                input.asOf(),
                input.prefix(),
                input.first(),
                input.middle(),
                input.secondMiddle(),
                input.last(),
                input.secondLast(),
                input.suffix(),
                input.degree(),
                input.type(),
                context.requestedBy(),
                context.requestedAt()
            )
        )
    );
  }

  void delete(final RequestContext context, final DeletePatientNameInput input) {
    this.service.using(input.patient(), found -> found.delete(
            new PatientCommand.DeleteNameInfo(
                input.patient(),
                input.sequence(),
                context.requestedBy(),
                context.requestedAt()
            )
        )
    );
  }
}

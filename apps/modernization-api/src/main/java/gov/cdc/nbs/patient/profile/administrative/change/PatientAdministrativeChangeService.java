package gov.cdc.nbs.patient.profile.administrative.change;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.profile.PatientProfileService;
import org.springframework.stereotype.Component;

@Component
class PatientAdministrativeChangeService {

  private final PatientProfileService service;

  PatientAdministrativeChangeService(final PatientProfileService service) {
    this.service = service;
  }

  void update(final RequestContext context, final UpdatePatientAdministrative input) {

    this.service.using(input.patient(), found -> found.update(
            new PatientCommand.UpdateAdministrativeInfo(
                input.patient(),
                input.asOf(),
                input.comment(),
                context.requestedBy(),
                context.requestedAt()
            )
        )
    );


  }
}

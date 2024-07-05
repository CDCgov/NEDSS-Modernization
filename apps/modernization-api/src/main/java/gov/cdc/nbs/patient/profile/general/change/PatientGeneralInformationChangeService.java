package gov.cdc.nbs.patient.profile.general.change;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.profile.PatientProfileService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
class PatientGeneralInformationChangeService {

  private final PatientProfileService service;

  PatientGeneralInformationChangeService(final PatientProfileService service) {
    this.service = service;
  }

  void update(final RequestContext context, final UpdateGeneralInformation input) {
    service.using(
        input.patient(),
        patient -> patient.update(
            new PatientCommand.UpdateGeneralInfo(
                input.patient(),
                input.asOf(),
                input.maritalStatus(),
                input.maternalMaidenName(),
                input.adultsInHouse(),
                input.childrenInHouse(),
                input.occupation(),
                input.educationLevel(),
                input.primaryLanguage(),
                input.speaksEnglish(),
                context.requestedBy(),
                context.requestedAt()
            )
        )
    );
  }


}

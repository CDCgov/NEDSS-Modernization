package gov.cdc.nbs.patient.profile.general.change;

import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.profile.PatientProfileService;
import org.springframework.stereotype.Component;

@Component
class PatientGeneralInformationChangeService {

  private final PatientProfileService service;
  private final PermissionScopeResolver resolver;

  PatientGeneralInformationChangeService(
      final PatientProfileService service,
      final PermissionScopeResolver resolver
  ) {
    this.service = service;
    this.resolver = resolver;
  }

  void update(final RequestContext context, final UpdateGeneralInformation input) {
    service.using(input.patient(), patient -> update(context, input, patient));
  }

  private void update(
      final RequestContext context,
      final UpdateGeneralInformation input,
      final Person patient
  ) {
    patient.update(
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
    );

    patient.associate(
        resolver,
        new PatientCommand.AssociateStateHIVCase(
            input.patient(),
            input.stateHIVCase(),
            context.requestedBy(),
            context.requestedAt()
        )
    );
  }

}

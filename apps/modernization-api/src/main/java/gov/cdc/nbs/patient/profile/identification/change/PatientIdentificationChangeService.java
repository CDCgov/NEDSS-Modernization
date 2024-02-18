package gov.cdc.nbs.patient.profile.identification.change;

import gov.cdc.nbs.entity.odse.EntityId;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.profile.PatientProfileService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PatientIdentificationChangeService {
    private final PatientProfileService patientProfileService;

    public PatientIdentificationChangeService(PatientProfileService patientProfileService) {
        this.patientProfileService = patientProfileService;
    }

    public PatientIdentificationAdded add(final RequestContext context, final NewPatientIdentificationInput input) {
        Person patient = patientProfileService.findPatientById(input.patient());

        EntityId added = patient.add(
            new PatientCommand.AddIdentification(
                input.patient(),
                input.asOf(),
                input.value(),
                input.authority(),
                input.type(),
                context.requestedBy(),
                context.requestedAt()
            )
        );

        return new PatientIdentificationAdded(input.patient(), added.getId().getEntityIdSeq());
    }

    public void update(final RequestContext context, final UpdatePatientIdentificationInput input) {
        this.patientProfileService.using(input.patient(), found -> found.update(
            new PatientCommand.UpdateIdentification(
                input.patient(),
                input.sequence(),
                input.asOf(),
                input.value(),
                input.authority(),
                input.type(),
                context.requestedBy(),
                context.requestedAt()
            )
        ));
    }

    public void delete(final RequestContext context, final DeletePatientIdentificationInput input) {
        this.patientProfileService.using(input.patient(), found -> found.delete(
            new PatientCommand.DeleteIdentification(
                input.patient(),
                input.sequence(),
                context.requestedBy(),
                context.requestedAt()
            )
        ));
    }
}

package gov.cdc.nbs.patient.profile.phone.change;

import gov.cdc.nbs.entity.odse.EntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.profile.PatientProfileService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PatientPhoneChangeService {

    private final IdGeneratorService idGeneratorService;
    private final PatientProfileService patientProfileService;

    public PatientPhoneChangeService(
            final IdGeneratorService idGeneratorService,
            final PatientProfileService patientProfileService
    ) {
        this.idGeneratorService = idGeneratorService;
        this.patientProfileService = patientProfileService;
    }

    public PatientPhoneAdded add(final RequestContext context, final NewPatientPhoneInput input) {
        Person patient = patientProfileService.findPatientById(input.patient());

        EntityLocatorParticipation added = patient.add(
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
                context.requestedAt()
            )
        );

        return new PatientPhoneAdded(input.patient(), added.getId().getLocatorUid());
    }

    private Long generateNbsId() {
        var generatedId = idGeneratorService.getNextValidId(IdGeneratorService.EntityType.NBS);
        return generatedId.getId();
    }

    public void update(final RequestContext context, final UpdatePatientPhoneInput input) {

        this.patientProfileService.using(input.patient(), found -> found.update(
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
                context.requestedAt()
            )
        ));

    }

    public void delete(final RequestContext context, final DeletePatientPhoneInput input) {

        this.patientProfileService.using(input.patient(), found -> found.delete(
            new PatientCommand.DeletePhone(
                input.patient(),
                input.id(),
                context.requestedBy(),
                context.requestedAt()
            )
        ));
    }
}

package gov.cdc.nbs.patient.profile.address.change;

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
public class PatientAddressChangeService {

    private final IdGeneratorService idGeneratorService;
    private final PatientProfileService patientProfileService;

    public PatientAddressChangeService(
            final IdGeneratorService idGeneratorService,
            final PatientProfileService patientProfileService
    ) {
        this.idGeneratorService = idGeneratorService;
        this.patientProfileService = patientProfileService;
    }

    public PatientAddressAdded add(final RequestContext context, final NewPatientAddressInput input) {
        Person patient = patientProfileService.findPatientById(input.patient());

        EntityLocatorParticipation added = patient.add(
            new PatientCommand.AddAddress(
                input.patient(),
                generateNbsId(),
                input.asOf(),
                input.type(),
                input.use(),
                input.address1(),
                input.address2(),
                input.city(),
                input.state(),
                input.zipcode(),
                input.county(),
                input.country(),
                input.censusTract(),
                input.comment(),
                context.requestedBy(),
                context.requestedAt()
            )
        );

        return new PatientAddressAdded(input.patient(), added.getId().getLocatorUid());
    }

    private Long generateNbsId() {
        var generatedId = idGeneratorService.getNextValidId(IdGeneratorService.EntityType.NBS);
        return generatedId.getId();
    }

    public void update(final RequestContext context, final UpdatePatientAddressInput input) {

        this.patientProfileService.using(input.patient(), found -> found.update(
            new PatientCommand.UpdateAddress(
                input.patient(),
                input.id(),
                input.asOf(),
                input.type(),
                input.use(),
                input.address1(),
                input.address2(),
                input.city(),
                input.state(),
                input.zipcode(),
                input.county(),
                input.country(),
                input.censusTract(),
                input.comment(),
                context.requestedBy(),
                context.requestedAt()
            )
        ));

    }

    public void delete(final RequestContext context, final DeletePatientAddressInput input) {

        this.patientProfileService.using(input.patient(), found -> found.delete(
            new PatientCommand.DeleteAddress(
                input.patient(),
                input.id(),
                context.requestedBy(),
                context.requestedAt()
            )
        ));
    }
}

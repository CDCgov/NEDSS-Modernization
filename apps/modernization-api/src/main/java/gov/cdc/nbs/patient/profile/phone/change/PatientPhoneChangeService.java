package gov.cdc.nbs.patient.profile.phone.change;

import gov.cdc.nbs.entity.odse.EntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

@Component
@Transactional
public class PatientPhoneChangeService {

    private final EntityManager entityManager;
    private final IdGeneratorService idGeneratorService;

    public PatientPhoneChangeService(
            final EntityManager entityManager,
            final IdGeneratorService idGeneratorService) {
        this.entityManager = entityManager;
        this.idGeneratorService = idGeneratorService;
    }

    public PatientPhoneAdded add(final RequestContext context, final NewPatientPhoneInput input) {
        Person patient = managed(input.patient());

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
                        context.requestedAt()));

        return new PatientPhoneAdded(input.patient(), added.getId().getLocatorUid());
    }

    private Long generateNbsId() {
        var generatedId = idGeneratorService.getNextValidId(IdGeneratorService.EntityType.NBS);
        return generatedId.getId();
    }

    public void update(final RequestContext context, final UpdatePatientPhoneInput input) {
        Person patient = managed(input.patient());

        patient.update(
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
                        context.requestedAt()));

    }

    public void delete(final RequestContext context, final DeletePatientPhoneInput input) {
        Person patient = managed(input.patient());

        patient.delete(
                new PatientCommand.DeletePhone(
                        input.patient(),
                        input.id(),
                        context.requestedBy(),
                        context.requestedAt()));
    }

    private Person managed(final long patient) {
        return this.entityManager.find(Person.class, patient);
    }
}

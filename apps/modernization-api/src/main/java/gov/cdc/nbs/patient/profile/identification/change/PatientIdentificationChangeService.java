package gov.cdc.nbs.patient.profile.identification.change;

import gov.cdc.nbs.entity.odse.EntityId;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Component
@Transactional
public class PatientIdentificationChangeService {

    private final EntityManager entityManager;

    public PatientIdentificationChangeService(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public PatientIdentificationAdded add(final RequestContext context, final NewPatientIdentificationInput input) {
        Person patient = managed(input.patient());

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
        Person patient = managed(input.patient());

        patient.update(
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
        );
    }

    public void delete(final RequestContext context, final DeletePatientIdentificationInput input) {
        Person patient = managed(input.patient());

        patient.delete(
            new PatientCommand.DeleteIdentification(
                input.patient(),
                input.sequence(),
                context.requestedBy(),
                context.requestedAt()
            )
        );
    }

    private Person managed(final long patient) {
        return this.entityManager.find(Person.class, patient);
    }
}

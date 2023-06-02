package gov.cdc.nbs.patient.delete;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientException;
import gov.cdc.nbs.patient.PatientNotFoundException;
import gov.cdc.nbs.patient.RequestContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Component
public class PatientDeleter {

    private final PatientAssociationCheck associationCheck;
    private final EntityManager entityManager;

    public PatientDeleter(
        final PatientAssociationCheck associationCheck,
        final EntityManager entityManager
    ) {
        this.associationCheck = associationCheck;
        this.entityManager = entityManager;
    }

    @Transactional
    public void delete(final RequestContext context, final long patient) throws PatientException {

        this.associationCheck.check(patient);

        Person found = this.entityManager.find(Person.class, patient);

        if (found == null) {
            throw new PatientNotFoundException(patient);
        }

        found.delete(
            new PatientCommand.Delete(
                patient,
                context.requestedBy(),
                context.requestedAt()
            )
        );

    }



}

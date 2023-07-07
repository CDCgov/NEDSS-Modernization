package gov.cdc.nbs.patient.delete;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.event.PatientEvent;
import gov.cdc.nbs.patient.PatientAssociationCountFinder;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientException;
import gov.cdc.nbs.patient.PatientNotFoundException;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.event.PatientEventEmitter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

@Component
public class PatientDeleter {

    private final EntityManager entityManager;
    private final PatientAssociationCountFinder finder;
    private final PatientEventEmitter emitter;

    public PatientDeleter(
            final EntityManager entityManager,
            final PatientAssociationCountFinder finder,
            final PatientEventEmitter emitter) {
        this.entityManager = entityManager;
        this.finder = finder;
        this.emitter = emitter;
    }

    @Transactional
    public void delete(
            final RequestContext context,
            final long patient) throws PatientException {

        Person found = this.entityManager.find(Person.class, patient);

        if (found == null) {
            throw new PatientNotFoundException(patient);
        }

        found.delete(
                new PatientCommand.Delete(
                        patient,
                        context.requestedBy(),
                        context.requestedAt()),
                finder);

        this.emitter.emit(
                new PatientEvent.Deleted(
                        found.getId(),
                        found.getLocalId(),
                        context.requestedBy(),
                        context.requestedAt()));
    }



}

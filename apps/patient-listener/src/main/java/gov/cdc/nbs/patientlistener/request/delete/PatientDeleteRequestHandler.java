package gov.cdc.nbs.patientlistener.request.delete;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.patientlistener.request.PatientNotFoundException;
import gov.cdc.nbs.patientlistener.request.PatientRequestStatusProducer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
public class PatientDeleteRequestHandler {

    private final PatientDeletionCheck check;
    private final PatientDeleter deleter;
    private final PatientRequestStatusProducer statusProducer;
    private final EntityManager entityManager;

    public PatientDeleteRequestHandler(
        final PatientDeletionCheck check,
        final PatientDeleter deleter,
        final PatientRequestStatusProducer statusProducer,
        final EntityManager entityManager
    ) {
        this.check = check;
        this.deleter = deleter;
        this.statusProducer = statusProducer;
        this.entityManager = entityManager;
    }

    /*
     * Sets a patients RecordStatus to 'LOG_DEL'
     */
    @Transactional
    public void handle(final PatientRequest.Delete request) {
        check.check(request);

        var person = findPerson(request.patientId(), request.requestId());
        deleter.delete(person, request.userId());
        statusProducer.successful(request.requestId(), "Successfully deleted patient", request.patientId());
    }

    private Person findPerson(final long patientId, final String requestId) {
        Person person = this.entityManager.find(Person.class, patientId);
        if (person == null) {
            throw new PatientNotFoundException(patientId, requestId);
        }
        return person;
    }


}

package gov.cdc.nbs.patient.profile.administrative.change;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

@Component
@Transactional
public class PatientAdministrativeChangeService {

    private final EntityManager entityManager;

    public PatientAdministrativeChangeService(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void update(final RequestContext context, final UpdatePatientAdministrative input) {
        Person patient = managed(input.patient());

        patient.update(
                new PatientCommand.UpdateAdministrativeInfo(
                        input.patient(),
                        input.asOf(),
                        input.comment(),
                        context.requestedBy(),
                        context.requestedAt()));
    }

    private Person managed(final long patient) {
        return this.entityManager.find(Person.class, patient);
    }

}

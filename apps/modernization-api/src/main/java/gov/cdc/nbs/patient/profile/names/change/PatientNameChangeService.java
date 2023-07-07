package gov.cdc.nbs.patient.profile.names.change;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonName;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

@Component
@Transactional
public class PatientNameChangeService {

    private final EntityManager entityManager;

    public PatientNameChangeService(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public PatientNameAdded add(final RequestContext context, final NewPatientNameInput request) {
        Person patient = managed(request.patient());

        PersonName added = patient.add(
                new PatientCommand.AddName(
                        request.patient(),
                        request.asOf(),
                        request.prefix(),
                        request.first(),
                        request.middle(),
                        request.secondMiddle(),
                        request.last(),
                        request.secondLast(),
                        request.suffix(),
                        request.degree(),
                        request.type(),
                        context.requestedBy(),
                        context.requestedAt()));

        return new PatientNameAdded(added.getId().getPersonUid(), added.getId().getPersonNameSeq());
    }


    public void update(final RequestContext context, final UpdatePatientNameInput input) {
        Person patient = managed(input.patient());

        patient.update(
                new PatientCommand.UpdateNameInfo(
                        input.patient(),
                        input.sequence(),
                        input.asOf(),
                        input.prefix(),
                        input.first(),
                        input.middle(),
                        input.secondMiddle(),
                        input.last(),
                        input.secondLast(),
                        input.suffix(),
                        input.degree(),
                        input.type(),
                        context.requestedBy(),
                        context.requestedAt()));
    }

    public void delete(final RequestContext context, final DeletePatientNameInput input) {
        Person patient = managed(input.patient());

        patient.delete(
                new PatientCommand.DeleteNameInfo(
                        input.patient(),
                        input.sequence(),
                        context.requestedBy(),
                        context.requestedAt()));
    }

    private Person managed(final long patient) {
        return this.entityManager.find(Person.class, patient);
    }
}

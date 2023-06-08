package gov.cdc.nbs.patient.profile.race.change;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.input.RaceInput;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Component
@Transactional
public class PatientRaceChangeService {

    private final EntityManager entityManager;

    PatientRaceChangeService(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void add(final RequestContext context, final RaceInput input) {
        Person patient = managed(input.getPatient());

        patient.add(
            new PatientCommand.AddRace(
                input.getPatient(),
                input.getAsOf(),
                input.getCategory(),
                input.getDetailed(),
                context.requestedBy(),
                context.requestedAt()
            )
        );
    }

    public void update(final RequestContext context, final RaceInput input) {
        Person patient = managed(input.getPatient());

        patient.update(
            new PatientCommand.UpdateRaceInfo(
                input.getPatient(),
                input.getAsOf(),
                input.getCategory(),
                input.getDetailed(),
                context.requestedBy(),
                context.requestedAt()
            )
        );
    }

    public void delete(final RequestContext context, final DeletePatientRace input) {
        Person patient = managed(input.patient());

        patient.delete(
            new PatientCommand.DeleteRaceInfo(
                input.patient(),
                input.category(),
                context.requestedBy(),
                context.requestedAt()
            )
        );
    }

    private Person managed(final long patient) {
        return this.entityManager.find(Person.class, patient);
    }
}

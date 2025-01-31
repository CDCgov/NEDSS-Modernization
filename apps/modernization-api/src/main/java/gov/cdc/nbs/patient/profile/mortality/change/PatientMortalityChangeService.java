package gov.cdc.nbs.patient.profile.mortality.change;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

@Component
@Transactional
class PatientMortalityChangeService {

    private final EntityManager entityManager;
    private final AddressIdentifierGenerator generator;

    public PatientMortalityChangeService(
        final EntityManager entityManager,
        final AddressIdentifierGenerator generator
    ) {
        this.entityManager = entityManager;
        this.generator = generator;
    }

    void update(final RequestContext context, final UpdatePatientMortality input) {

        Person patient = managed(input.patient());

        patient.update(
            new PatientCommand.UpdateMortality(
                input.patient(),
                input.asOf(),
                input.deceased(),
                input.deceasedOn(),
                input.city(),
                input.state(),
                input.county(),
                input.country(),
                context.requestedBy(),
                context.requestedAt()
            ),
            generator
        );
    }

    private Person managed(final long patient) {
        return this.entityManager.find(Person.class, patient);
    }

}

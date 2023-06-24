package gov.cdc.nbs.patient.profile.birth.change;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Component
@Transactional
public class PatientBirthAndGenderChangeService {

    private final EntityManager entityManager;
    private final AddressIdentifierGenerator generator;

    public PatientBirthAndGenderChangeService(
        final EntityManager entityManager,
        final AddressIdentifierGenerator generator
    ) {
        this.entityManager = entityManager;
        this.generator = generator;
    }

    public void update(final RequestContext context, final UpdateBirthAndGender input) {

        Person patient = managed(input.patient());

        UpdateBirthAndGender.Birth birth = input.birth();

        if (birth != null) {
            patient.update(
                new PatientCommand.UpdateBirth(
                    input.patient(),
                    input.asOf(),
                    birth.bornOn(),
                    birth.gender(),
                    birth.multipleBirth(),
                    birth.birthOrder(),
                    birth.city(),
                    birth.state(),
                    birth.county(),
                    birth.country(),
                    context.requestedBy(),
                    context.requestedAt()
                ),
                generator
            );
        }

        UpdateBirthAndGender.Gender gender = input.gender();

        if (gender != null) {
            patient.update(
                new PatientCommand.UpdateGender(
                    input.patient(),
                    input.asOf(),
                    gender.current(),
                    gender.unknownReason(),
                    gender.preferred(),
                    gender.additional(),
                    context.requestedBy(),
                    context.requestedAt()
                )
            );
        }
    }

    private Person managed(final long patient) {
        return this.entityManager.find(Person.class, patient);
    }

}

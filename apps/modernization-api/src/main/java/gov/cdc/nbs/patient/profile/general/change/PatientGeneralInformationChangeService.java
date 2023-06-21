package gov.cdc.nbs.patient.profile.general.change;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Component
@Transactional
public class PatientGeneralInformationChangeService {

    private final EntityManager entityManager;

    public PatientGeneralInformationChangeService(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void update(final RequestContext context, final UpdateGeneralInformation input) {
        Person patient = managed(input.patient());

        patient.update(
            new PatientCommand.UpdateGeneralInfo(
                input.patient(),
                input.asOf(),
                input.maritalStatus(),
                input.maternalMaidenName(),
                input.adultsInHouse(),
                input.childrenInHouse(),
                input.occupation(),
                input.educationLevel(),
                input.primaryLanguage(),
                input.speaksEnglish(),
                input.stateHIVCase(),
                context.requestedBy(),
                context.requestedAt()
            )
        );
    }

    private Person managed(final long patient) {
        return this.entityManager.find(Person.class, patient);
    }

}

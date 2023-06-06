package gov.cdc.nbs.patient.profile.ethnicity;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonEthnicGroup;
import gov.cdc.nbs.message.patient.input.EthnicityInput;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Transactional
public class PatientEthnicityService {

    private final EntityManager entityManager;

    PatientEthnicityService(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public void update(final RequestContext context, final EthnicityInput input) {

        Person patient = managed(input.getPatient());

        patient.update(
            new PatientCommand.UpdateEthnicityInfo(
                input.getPatient(),
                input.getAsOf(),
                input.getEthnicGroup(),
                input.getUnknownReason(),
                context.requestedBy(),
                context.requestedAt()
            )
        );

        List<String> detailed = input.getDetailed();

        List<String> existing = patient.getEthnicity().ethnicities()
            .stream()
            .map(group -> group.getId().getEthnicGroupCd())
            .toList();


        ArrayList<String> added = new ArrayList<>(detailed);
        added.removeAll(existing);

        added.stream()
            .map(detail -> asAdded(context, input.getPatient(), detail))
            .forEach(patient::add);

        ArrayList<String> removed = new ArrayList<>(existing);
        removed.removeAll(detailed);

        removed.stream()
            .map(detail -> asRemove(context, input.getPatient(), detail))
            .forEach(patient::remove);

    }

    private PatientCommand.AddDetailedEthnicity asAdded(
        final RequestContext context,
        final long patient,
        final String detailed
        ) {
        return new PatientCommand.AddDetailedEthnicity(
            patient,
            detailed,
            context.requestedBy(),
            context.requestedAt()
        );
    }

    private PatientCommand.RemoveDetailedEthnicity asRemove(
        final RequestContext context,
        final long patient,
        final String detailed
    ) {
        return new PatientCommand.RemoveDetailedEthnicity(
            patient,
            detailed,
            context.requestedBy(),
            context.requestedAt()
        );
    }

    private Person managed(final long patient) {
        return this.entityManager.find(Person.class, patient);
    }
}

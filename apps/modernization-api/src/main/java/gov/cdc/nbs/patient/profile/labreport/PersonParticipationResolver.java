package gov.cdc.nbs.patient.profile.labreport;

import gov.cdc.nbs.entity.odse.PersonParticipation;
import gov.cdc.nbs.repository.PersonParticipationRepository;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
class PersonParticipationResolver {
    private final PersonParticipationRepository personParticipationRepository;

    PersonParticipationResolver(final PersonParticipationRepository personParticipationRepository) {
        this.personParticipationRepository = personParticipationRepository;
    }

    @SchemaMapping("personParticipations")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    List<PersonParticipation> resolve(final long observationUid) {
        return personParticipationRepository.findAllPersonParticipationsByObservationUid(observationUid);
    }
}

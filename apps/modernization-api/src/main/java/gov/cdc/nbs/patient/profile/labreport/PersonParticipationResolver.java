
package gov.cdc.nbs.patient.profile.labreport;

import gov.cdc.nbs.entity.projections.PersonParticipation2;
import gov.cdc.nbs.repository.PersonRepository;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import gov.cdc.nbs.entity.projections.PatientLabReport;
import java.util.List;

@Controller
class PersonParticipationResolver {
    private final PersonRepository personParticipationRepository;

    PersonParticipationResolver(final PersonRepository personParticipationRepository) {
        this.personParticipationRepository = personParticipationRepository;
    }

    @SchemaMapping(typeName = "PatientLabReport", field = "personParticipations")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    List<PersonParticipation2> resolve(PatientLabReport labreport) {
        return personParticipationRepository.findAllPersonParticipationsByObservationUid(labreport.getObservationUid());
    }
}

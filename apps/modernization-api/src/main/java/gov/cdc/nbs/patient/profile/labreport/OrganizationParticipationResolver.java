package gov.cdc.nbs.patient.profile.labreport;

import gov.cdc.nbs.entity.odse.OrganizationParticipation;
import gov.cdc.nbs.repository.OrganizationParticipationRepository;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
class OrganizationParticipationResolver {
    private final OrganizationParticipationRepository organizationParticipationRepository;

    OrganizationParticipationResolver(final OrganizationParticipationRepository organizationParticipationRepository) {
        this.organizationParticipationRepository = organizationParticipationRepository;
    }

    @SchemaMapping("organizationParticipations")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    List<OrganizationParticipation> resolve(final long observationUid) {
        return organizationParticipationRepository.findAllOrganizationParticipationsByObservationUid(observationUid);
    }
}


package gov.cdc.nbs.patient.profile.labreport;

import gov.cdc.nbs.entity.projections.OrganizationParticipation2;
import gov.cdc.nbs.repository.ParticipationRepository;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import gov.cdc.nbs.entity.projections.PatientLabReport;
import java.util.List;

@Controller
class OrganizationParticipationResolver {
    private final ParticipationRepository organizationParticipationRepository;

    OrganizationParticipationResolver(final ParticipationRepository organizationParticipationRepository) {
        this.organizationParticipationRepository = organizationParticipationRepository;
    }

    @SchemaMapping(typeName = "PatientLabReport", field = "organizationParticipations")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    List<OrganizationParticipation2> resolve(PatientLabReport labreport) {
        return organizationParticipationRepository
                .findAllOrganizationParticipationsByObservationUid(labreport.getObservationUid());
    }
}

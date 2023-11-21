
package gov.cdc.nbs.patient.profile.labreport;

import gov.cdc.nbs.entity.projections.Investigation2;
import gov.cdc.nbs.repository.PublicHealthCaseRepository;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
class PublicHealthCaseResolver {
    private final PublicHealthCaseRepository investigationRepository;

    PublicHealthCaseResolver(final PublicHealthCaseRepository investigationRepository) {
        this.investigationRepository = investigationRepository;
    }

    @SchemaMapping(typeName = "LabReport2", field = "associatedInvestigations2")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    List<Investigation2> resolve(LabReport2 labreport) {
        return investigationRepository.findAllInvestigationsByObservationUid(labreport.observationUid());
    }
}

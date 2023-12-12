
package gov.cdc.nbs.patient.profile.labreport;

import gov.cdc.nbs.repository.PublicHealthCaseRepository;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import gov.cdc.nbs.entity.projections.AssociatedInvestigation2;
import gov.cdc.nbs.entity.projections.PatientLabReport;
import java.util.List;

@Controller
class PublicHealthCaseResolver {
    private final PublicHealthCaseRepository investigationRepository;

    PublicHealthCaseResolver(final PublicHealthCaseRepository investigationRepository) {
        this.investigationRepository = investigationRepository;
    }

    @SchemaMapping(typeName = "PatientLabReport", field = "associatedInvestigations")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    List<AssociatedInvestigation2> resolve(PatientLabReport labreport) {
        return investigationRepository.findAllInvestigationsByObservationUid(labreport.getObservationUid());
    }
}

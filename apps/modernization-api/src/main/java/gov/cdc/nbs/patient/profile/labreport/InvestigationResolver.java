package gov.cdc.nbs.patient.profile.labreport;

import gov.cdc.nbs.entity.odse.Investigation;
import gov.cdc.nbs.repository.InvestigationRepository;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
class InvestigationResolver {
    private final InvestigationRepository investigationRepository;

    InvestigationResolver(final InvestigationRepository investigationRepository) {
        this.investigationRepository = investigationRepository;
    }

    @SchemaMapping("investigations")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    List<Investigation> resolve(final long observationUid) {
        return investigationRepository.findAllInvestigationsByObservationUid(observationUid);
    }
}

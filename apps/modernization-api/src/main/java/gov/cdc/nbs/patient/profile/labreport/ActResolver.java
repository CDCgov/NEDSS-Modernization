package gov.cdc.nbs.patient.profile.labreport;

import gov.cdc.nbs.entity.odse.Act;
import gov.cdc.nbs.repository.ActRepository;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import java.util.Optional;

@Controller
class ActResolver {
    private final ActRepository actRepository;

    ActResolver(final ActRepository actRepository) {
        this.actRepository = actRepository;
    }

    @SchemaMapping(typeName = "LabReport2", field = "act")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    Optional<Act> resolve(final long actId) {
        return actRepository.findById(actId);
    }
}

package gov.cdc.nbs.patient.profile.labreport;

import gov.cdc.nbs.entity.odse.ActId;
import gov.cdc.nbs.repository.ActIdRepository;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
class ActIdResolver {
    private final ActIdRepository actIdRepository;

    ActIdResolver(final ActIdRepository actIdRepository) {
        this.actIdRepository = actIdRepository;
    }

    @SchemaMapping("actids")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    List<ActId> resolve(final long actUid) {
        return actIdRepository.findAllByActUid(actUid);
    }
}

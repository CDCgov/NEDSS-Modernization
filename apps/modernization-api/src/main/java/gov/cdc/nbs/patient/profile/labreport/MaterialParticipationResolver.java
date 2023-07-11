package gov.cdc.nbs.patient.profile.labreport;

import gov.cdc.nbs.entity.odse.MaterialParticipation;
import gov.cdc.nbs.repository.MaterialParticipationRepository;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
class MaterialParticipationResolver {
    private final MaterialParticipationRepository materialParticipationRepository;

    MaterialParticipationResolver(final MaterialParticipationRepository materialParticipationRepository) {
        this.materialParticipationRepository = materialParticipationRepository;
    }

    @SchemaMapping("materialParticipations")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    List<MaterialParticipation> resolve(final long observationUid) {
        return materialParticipationRepository.findAllMaterialParticipationsByObservationUid(observationUid);
    }
}

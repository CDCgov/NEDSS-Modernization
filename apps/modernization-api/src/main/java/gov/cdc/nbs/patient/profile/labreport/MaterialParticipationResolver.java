package gov.cdc.nbs.patient.profile.labreport;

import gov.cdc.nbs.entity.projections.MaterialParticipation2;
import gov.cdc.nbs.repository.ParticipationRepository;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import java.util.List;
import gov.cdc.nbs.entity.projections.LabReport2;

@Controller
class MaterialParticipationResolver {
    private final ParticipationRepository materialParticipationRepository;

    MaterialParticipationResolver(final ParticipationRepository materialParticipationRepository) {
        this.materialParticipationRepository = materialParticipationRepository;
    }

    @SchemaMapping(typeName = "LabReport2", field = "materialParticipations2")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    List<MaterialParticipation2> resolve(LabReport2 labreport) {
        return materialParticipationRepository
                .findAllMaterialParticipationsByObservationUid(labreport.getObservationUid());
    }
}

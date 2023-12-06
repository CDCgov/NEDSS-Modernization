package gov.cdc.nbs.patient.profile.labreport;

import gov.cdc.nbs.entity.projections.ActId2;
import gov.cdc.nbs.repository.ActIdRepository;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import gov.cdc.nbs.entity.projections.PatientLabReport;
import java.util.List;

@Controller
class ActIdResolver {
    private final ActIdRepository actIdRepository;

    ActIdResolver(final ActIdRepository actIdRepository) {
        this.actIdRepository = actIdRepository;
    }

    @SchemaMapping(typeName = "PatientLabReport", field = "actIds2")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    List<ActId2> resolve(PatientLabReport labreport) {
        return actIdRepository.findAllByActUid(labreport.getObservationUid());
    }
}

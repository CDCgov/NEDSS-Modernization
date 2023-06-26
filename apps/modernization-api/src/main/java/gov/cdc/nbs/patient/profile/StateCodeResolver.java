package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.entity.odse.PostalLocator;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import gov.cdc.nbs.repository.StateCodeRepository;
import gov.cdc.nbs.entity.srte.StateCode;

@Controller
class StateCodeResolver {
    @Autowired
    private StateCodeRepository stateCodeRepository;

    @SchemaMapping("stateCode")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    Optional<StateCode> resolve(final PostalLocator locator) {
        return stateCodeRepository.findById(locator.getStateCd());
    }
}

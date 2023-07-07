package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.entity.odse.Locator;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.srte.StateCode;
import gov.cdc.nbs.repository.StateCodeRepository;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
class StateCodeResolver {

    private final StateCodeRepository stateCodeRepository;

    StateCodeResolver(final StateCodeRepository stateCodeRepository) {
        this.stateCodeRepository = stateCodeRepository;
    }

    @SchemaMapping(typeName = "Locator", field = "stateCode")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    Optional<StateCode> resolve(final Locator locator) {
        if (locator instanceof PostalLocator address && address.getStateCd() != null) {

            return stateCodeRepository.findById(address.getStateCd());
        }
        return Optional.empty();
    }
}

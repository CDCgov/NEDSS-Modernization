package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.entity.odse.Locator;
import gov.cdc.nbs.entity.odse.PostalLocator;
import java.util.Optional;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import gov.cdc.nbs.repository.CountryCodeRepository;
import gov.cdc.nbs.entity.srte.CountryCode;

@Controller
class CountryCodeResolver {

    private final CountryCodeRepository countryCodeRepository;

    CountryCodeResolver(final CountryCodeRepository countryCodeRepository) {
        this.countryCodeRepository = countryCodeRepository;
    }

    @SchemaMapping(typeName = "Locator", field = "countryCode")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    Optional<CountryCode> resolve(final Locator locator) {
        if (locator instanceof PostalLocator address && address.getCntryCd() != null) {

            return countryCodeRepository.findById(address.getCntryCd());
        }
        return Optional.empty();
    }
}

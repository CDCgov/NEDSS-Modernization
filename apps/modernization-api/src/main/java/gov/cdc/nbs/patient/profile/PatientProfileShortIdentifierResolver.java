package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.OptionalLong;

@Controller
class PatientProfileShortIdentifierResolver {

    private final PatientShortIdentifierResolver resolver;

    PatientProfileShortIdentifierResolver(final PatientShortIdentifierResolver resolver) {
        this.resolver = resolver;
    }

    @SchemaMapping("shortId")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    OptionalLong resolve(final PatientProfile profile) {
        return this.resolver.resolve(profile.local());
    }
}

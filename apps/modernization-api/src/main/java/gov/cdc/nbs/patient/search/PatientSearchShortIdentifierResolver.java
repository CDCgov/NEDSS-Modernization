package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.OptionalLong;

@Controller
class PatientSearchShortIdentifierResolver {

    private final PatientShortIdentifierResolver resolver;

    PatientSearchShortIdentifierResolver(final PatientShortIdentifierResolver resolver) {
        this.resolver = resolver;
    }

    @SchemaMapping("shortId")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    OptionalLong resolve(final Person patient) {
        return this.resolver.resolve(patient.getLocalId());
    }
}

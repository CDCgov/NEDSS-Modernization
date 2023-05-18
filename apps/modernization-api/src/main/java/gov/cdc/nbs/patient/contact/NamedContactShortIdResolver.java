package gov.cdc.nbs.patient.contact;

import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.OptionalLong;

@Controller
class NamedContactShortIdResolver {

    private final PatientShortIdentifierResolver resolver;

    NamedContactShortIdResolver(final PatientShortIdentifierResolver resolver) {
        this.resolver = resolver;
    }

    @SchemaMapping(typeName = "NamedContact", field = "id")
    OptionalLong resolve(final PatientContacts.NamedContact named) {
        return this.resolver.resolve(named.local());
    }
}

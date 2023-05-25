package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.patient.delete.PatientIsDeletableResolver;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
class PatientProfileDeletableResolver {

    private final PatientIsDeletableResolver resolver;

    PatientProfileDeletableResolver(final PatientIsDeletableResolver resolver) {
        this.resolver = resolver;
    }

    @SchemaMapping("deletable")
    boolean resolve(final PatientProfile profile) {
        return resolver.canDelete(profile.id());
    }

}

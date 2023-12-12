package gov.cdc.nbs.patient.profile.labreport;

import gov.cdc.nbs.entity.projections.PersonParticipation2;
import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
class PersonParticipationShortIdResolver {

    private final PatientShortIdentifierResolver resolver;

    PersonParticipationShortIdResolver(final PatientShortIdentifierResolver resolver) {
        this.resolver = resolver;
    }

    @SchemaMapping(typeName = "PersonParticipation2", field = "shortId")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    Long resolve(final PersonParticipation2 participation) {
        return this.resolver.resolve(participation.getLocalId()).orElse(0);
    }
}

package gov.cdc.nbs.patient.profile.redirect.incoming;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.identifier.PatientIdentifierFinder;
import gov.cdc.nbs.patient.profile.redirect.ReturningPatientCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@Component
public class ModernizedPatientProfileRedirectResolver {

    private final IncomingPatientIdentifierResolver resolver;
    private final PatientIdentifierFinder finder;

    public ModernizedPatientProfileRedirectResolver(
        final IncomingPatientIdentifierResolver resolver,
        final PatientIdentifierFinder finder
    ) {
        this.resolver = resolver;
        this.finder = finder;
    }

    /**
     * Resolves a redirect to the Modernized Patient Profile using the patient identifier from the
     * {@code Returning-Patient} cookie.
     *
     * @param request The incoming Patient Profile Request
     * @return A {@link ResponseEntity} that redirects to the Modernized Patient Profile
     */
    public ResponseEntity<Void> fromReturnPatient(final HttpServletRequest request) {
        URI location = resolver.fromReturningPatient(request)
            .flatMap(finder::findById)
            .map(this::patientProfile)
            .orElseGet(this::advancedSearch);

        return redirectTo(location);
    }

    /**
     * Resolves a redirect to the Modernized Patient Profile using the patient identifier from either the {@code MPRUid}
     * or {@code uid} query parameter.
     *
     * @param request The incoming Patient Profile Request
     * @return A {@link ResponseEntity} that redirects to the Modernized Patient Profile
     */
    public ResponseEntity<Void> fromPatientParameters(final HttpServletRequest request) {
        URI location = resolver.fromQueryParams(request)
            .flatMap(finder::findById)
            .map(this::patientProfile)
            .orElseGet(this::advancedSearch);

        return redirectTo(location);
    }

    private URI patientProfile(final PatientIdentifier patient) {
        return UriComponentsBuilder.fromPath("/")
            .path("patient-profile/{identifier}")
            .buildAndExpand(patient.shortId())
            .toUri();
    }

    private URI advancedSearch() {
        return UriComponentsBuilder.fromPath("/")
            .path("advanced-search")
            .build()
            .toUri();
    }

    private ResponseEntity<Void> redirectTo(final URI location) {
        return ResponseEntity.status(HttpStatus.SEE_OTHER)
            .location(location)
            .headers(ReturningPatientCookie.empty()::apply)
            .build();
    }

}

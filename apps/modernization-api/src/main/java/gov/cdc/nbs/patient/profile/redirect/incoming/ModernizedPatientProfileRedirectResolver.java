package gov.cdc.nbs.patient.profile.redirect.incoming;

import gov.cdc.nbs.patient.profile.redirect.PatientIdentifierResolver;
import gov.cdc.nbs.patient.profile.redirect.ReturningPatientCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@Component
public class ModernizedPatientProfileRedirectResolver {

    /**
     * Resolves a redirect to the Modernized Patient Profile using the patient identifier from the
     * {@code Returning-Patient} cookie.
     *
     * @param request The incoming Patient Profile Request
     * @return A {@link ResponseEntity} that redirects to the Modernized Patient Profile
     */
    public ResponseEntity<Void> fromReturnPatient(final HttpServletRequest request) {
        String returning = PatientIdentifierResolver.fromReturningPatient(request)
            .orElse(null);

        return forPatient(returning);
    }

    /**
     * Resolves a redirect to the Modernized Patient Profile using the patient identifier from either the {@code MPRUid}
     * or {@code uid} query parameter.
     *
     * @param request The incoming Patient Profile Request
     * @return A {@link ResponseEntity} that redirects to the Modernized Patient Profile
     */
    public ResponseEntity<Void> fromPatientParameters(final HttpServletRequest request) {
        String returning = PatientIdentifierResolver.fromQueryParams(request)
            .orElse(null);

        return forPatient(returning);
    }

    private ResponseEntity<Void> forPatient(final String patient) {
        URI location = UriComponentsBuilder.fromPath("/")
            .path("patient-profile/{identifier}")
            .buildAndExpand(patient)
            .toUri();

        return ResponseEntity.status(HttpStatus.SEE_OTHER)
            .location(location)
            .headers(ReturningPatientCookie.empty()::apply)
            .build();
    }

}

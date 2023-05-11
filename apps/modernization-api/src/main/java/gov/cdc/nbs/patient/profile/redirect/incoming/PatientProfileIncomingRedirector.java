package gov.cdc.nbs.patient.profile.redirect.incoming;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

@RestController
class PatientProfileIncomingRedirector {

    private final ModernizedPatientProfileRedirectResolver resolver;

    PatientProfileIncomingRedirector(final ModernizedPatientProfileRedirectResolver resolver) {
        this.resolver = resolver;
    }

    /**
     * Receives proxied View Patient Profile requests from Classic NBS.  POST requests from Patient Profile typically
     * include the patient identifier in tas a query parameter.
     *
     * @param request The {@link HttpServletRequest} from Classic NBS
     * @return A {@link ResponseEntity} redirecting to the Modernized Patient Profile
     */
    @ApiIgnore
    @PostMapping("/nbs/redirect/patientProfile")
    ResponseEntity<Void> redirectedPatientProfilePost(final HttpServletRequest request) {
        return resolver.fromPatientParameters(request);
    }

    /**
     * Receives proxied View Patient Profile requests from Classic NBS.
     *
     * @param request The {@link HttpServletRequest} from Classic NBS
     * @return A {@link ResponseEntity} redirecting to the Modernized Patient Profile
     */
    @ApiIgnore
    @GetMapping("/nbs/redirect/patientProfile")
    ResponseEntity<Void> redirectedPatientProfileGet(final HttpServletRequest request) {
        return resolver.fromReturnPatient(request);
    }
}

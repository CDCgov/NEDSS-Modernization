package gov.cdc.nbs.patient.profile.redirect.incoming;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
class PatientProfileIncomingRedirection {

  private final ModernizedPatientProfileRedirectResolver resolver;

  PatientProfileIncomingRedirection(final ModernizedPatientProfileRedirectResolver resolver) {
    this.resolver = resolver;
  }

  /**
   * Receives proxied View Patient Profile requests from Classic NBS. POST
   * requests from Patient Profile typically
   * include the patient identifier in tas a query parameter.
   *
   * @param request The {@link HttpServletRequest} from Classic NBS
   * @return A {@link ResponseEntity} redirecting to the Modernized Patient
   *         Profile
   */
  @GetMapping("/nbs/redirect/patient/file")
  ResponseEntity<Void> redirectedPatientProfileGET(final HttpServletRequest request) {
    return resolver.fromPatientParameters(request);
  }

  /**
   * Receives proxied View Patient Profile requests from Classic NBS. GET requests
   * from Patient Profile typically
   * include the patient identifier in tas a query parameter.
   *
   * @param request The {@link HttpServletRequest} from Classic NBS
   * @return A {@link ResponseEntity} redirecting to the Modernized Patient
   *         Profile
   */
  @PostMapping("/nbs/redirect/patient/file")
  ResponseEntity<Void> redirectedPatientProfilePOST(final HttpServletRequest request) {
    return resolver.fromPatientParameters(request);
  }

  @GetMapping("/nbs/redirect/patient/file/{tab}/return")
  ResponseEntity<Void> redirectPatientProfileReturnGET(
      final HttpServletRequest request,
      @PathVariable(required = false) final String tab) {
    return resolver.fromReturnPatient(request, tab);
  }

  @PostMapping("/nbs/redirect/patient/file/{tab}/return")
  ResponseEntity<Void> redirectPatientProfileReturnPOST(
      final HttpServletRequest request,
      @PathVariable(required = false) final String tab) {
    return resolver.fromReturnPatient(request, tab);
  }

}

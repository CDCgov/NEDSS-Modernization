package gov.cdc.nbs.patient.profile.redirect.incoming;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
class PatientProfileIncomingRedirection {

  private final ModernizedPatientProfileRedirectResolver resolver;

  PatientProfileIncomingRedirection(final ModernizedPatientProfileRedirectResolver resolver) {
    this.resolver = resolver;
  }

  /**
   * Receives proxied View Patient Profile requests from Classic NBS.  POST requests from Patient Profile typically
   * include the patient identifier in tas a query parameter.
   *
   * @param request The {@link HttpServletRequest} from Classic NBS
   * @return A {@link ResponseEntity} redirecting to the Modernized Patient Profile
   */
  @RequestMapping(
      method = {RequestMethod.GET, RequestMethod.POST},
      path = {"/nbs/redirect/patientProfile", "/nbs/redirect/patient/profile"}
  )
  ResponseEntity<Void> redirectedPatientProfile(final HttpServletRequest request) {
    return resolver.fromPatientParameters(request);
  }

  @RequestMapping(
      method = {RequestMethod.GET, RequestMethod.POST},
      path = {"/nbs/redirect/patientProfile/return", "/nbs/redirect/patientProfile/{tab}/return"}
  )
  ResponseEntity<Void> redirectPatientProfileReturnPOST(
      final HttpServletRequest request,
      @PathVariable(required = false) final String tab
  ) {
    return resolver.fromReturnPatient(request, tab);
  }

}

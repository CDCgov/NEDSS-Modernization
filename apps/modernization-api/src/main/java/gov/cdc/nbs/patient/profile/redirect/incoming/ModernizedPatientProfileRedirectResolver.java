package gov.cdc.nbs.patient.profile.redirect.incoming;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ModernizedPatientProfileRedirectResolver {

  private final IncomingPatientResolver incoming;
  private final RequestedIncomingPatientResolver requested;

  ModernizedPatientProfileRedirectResolver(
      final IncomingPatientResolver incoming, final RequestedIncomingPatientResolver requested) {
    this.incoming = incoming;
    this.requested = requested;
  }

  /**
   * Resolves a redirect to the Modernized Patient Profile using the identifier from the {@code
   * Return-Patient} cookie.
   *
   * @param request The incoming Patient Profile Request
   * @return A {@link ResponseEntity} that redirects to the Modernized Patient Profile
   */
  public ResponseEntity<Void> fromReturnPatient(final HttpServletRequest request) {
    return incoming
        .from(request)
        .map(ModernizedPatientProfileRedirect::forPatient)
        .orElse(ModernizedPatientProfileRedirect.fallback())
        .redirect();
  }

  /**
   * Resolves a redirect to the Modernized Patient Profile using the identifier from the {@code
   * Return-Patient} cookie.
   *
   * @param request The incoming Patient Profile Request
   * @param tab The tab within the Patient Profile to show
   * @return A {@link ResponseEntity} that redirects to the Modernized Patient Profile
   */
  public ResponseEntity<Void> fromReturnPatient(
      final HttpServletRequest request, final String tab) {
    return incoming
        .from(request)
        .map(resolved -> ModernizedPatientProfileRedirect.forPatient(resolved, tab))
        .orElse(ModernizedPatientProfileRedirect.fallback())
        .redirect();
  }

  /**
   * Resolves a redirect to the Modernized Patient Profile using the identifier from either the
   * {@code MPRUid} or {@code uid} query parameter.
   *
   * @param request The incoming Patient Profile Request
   * @return A {@link ResponseEntity} that redirects to the Modernized Patient Profile
   */
  ResponseEntity<Void> fromPatientParameters(final HttpServletRequest request) {
    return redirected(requested.from(request));
  }

  private ResponseEntity<Void> redirected(final Optional<IncomingPatient> incoming) {
    return incoming
        .map(ModernizedPatientProfileRedirect::forPatient)
        .orElse(ModernizedPatientProfileRedirect.fallback())
        .redirect();
  }
}

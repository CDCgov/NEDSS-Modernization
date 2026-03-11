package gov.cdc.nbs.patient.profile.redirect.incoming;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
class IncomingPatientResolver {

  private final ReturningIncomingPatientResolver returning;
  private final ActionIncomingPatientResolver action;

  IncomingPatientResolver(
      final ReturningIncomingPatientResolver returning,
      final ActionIncomingPatientResolver action) {
    this.returning = returning;
    this.action = action;
  }

  Optional<IncomingPatient> from(final HttpServletRequest request) {
    return returning.from(request).or(() -> action.from(request));
  }
}

package gov.cdc.nbs.patient.profile.redirect.incoming;

import gov.cdc.nbs.maybe.MaybeLong;
import gov.cdc.nbs.patient.profile.redirect.PatientActionCookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
class ActionIncomingPatientResolver {

  private final IncomingPatientFromActionFinder finder;

  ActionIncomingPatientResolver(final IncomingPatientFromActionFinder finder) {
    this.finder = finder;
  }

  Optional<IncomingPatient> from(final HttpServletRequest request) {
    return PatientActionCookie.resolve(request.getCookies())
        .map(PatientActionCookie::action)
        .flatMap(MaybeLong::from)
        .flatMap(finder::find);
  }
}

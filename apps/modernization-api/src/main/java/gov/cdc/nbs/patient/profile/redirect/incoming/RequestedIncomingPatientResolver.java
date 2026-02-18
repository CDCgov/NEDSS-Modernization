package gov.cdc.nbs.patient.profile.redirect.incoming;

import gov.cdc.nbs.maybe.MaybeLong;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class RequestedIncomingPatientResolver {

  private static final String PATIENT_PARENT_ID = "MPRUid";
  private static final String PATIENT_ID = "uid";

  private final IncomingPatientFinder finder;

  public RequestedIncomingPatientResolver(final IncomingPatientFinder finder) {
    this.finder = finder;
  }

  public Optional<IncomingPatient> from(final HttpServletRequest request) {
    return MaybeLong.from(request.getParameter(PATIENT_PARENT_ID))
        .or(() -> MaybeLong.from(request.getParameter(PATIENT_ID)))
        .flatMap(finder::find);
  }

  public Optional<IncomingPatient> from(final long identifier) {
    return finder.find(identifier);
  }
}

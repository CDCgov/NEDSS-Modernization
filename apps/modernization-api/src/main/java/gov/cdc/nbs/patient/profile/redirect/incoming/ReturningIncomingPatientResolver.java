package gov.cdc.nbs.patient.profile.redirect.incoming;

import gov.cdc.nbs.maybe.MaybeLong;
import gov.cdc.nbs.patient.profile.redirect.ReturningPatientCookie;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
class ReturningIncomingPatientResolver {

    private final IncomingPatientFinder finder;

    ReturningIncomingPatientResolver(final IncomingPatientFinder finder) {
        this.finder = finder;
    }

    Optional<IncomingPatient> from(final HttpServletRequest request) {
        return ReturningPatientCookie.resolve(request.getCookies())
                .map(ReturningPatientCookie::patient)
                .flatMap(MaybeLong::from)
                .flatMap(finder::find);
    }

}

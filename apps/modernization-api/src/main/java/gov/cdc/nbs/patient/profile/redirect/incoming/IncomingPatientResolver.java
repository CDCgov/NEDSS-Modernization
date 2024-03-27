package gov.cdc.nbs.patient.profile.redirect.incoming;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
class IncomingPatientResolver {

    private final ReturningIncomingPatientResolver returning;
    private final ActionIncomingPatientResolver action;

    IncomingPatientResolver(
            final ReturningIncomingPatientResolver returning,
            final ActionIncomingPatientResolver action
    ) {
        this.returning = returning;
        this.action = action;
    }

    Optional<IncomingPatient> from(final HttpServletRequest request) {
        return returning.from(request).or(() -> action.from(request));
    }

}

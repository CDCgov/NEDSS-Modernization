package gov.cdc.nbs.patient.profile.redirect.incoming;

import gov.cdc.nbs.patient.profile.redirect.ReturningPatientCookie;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
class IncomingPatientIdentifierResolver {

    private static Optional<Long> maybeLong(final String value) {
        if (value == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(Long.valueOf(value));
        } catch (NumberFormatException exception) {
            return Optional.empty();
        }

    }

    private static final String PATIENT_PARENT_ID = "MPRUid";
    private static final String PATIENT_ID = "uid";

    Optional<Long> fromQueryParams(final HttpServletRequest request) {
        return maybeLong(request.getParameter(PATIENT_PARENT_ID)).or(() -> maybeLong(request.getParameter(PATIENT_ID)));

    }

    Optional<Long> fromReturningPatient(final HttpServletRequest request) {
        return ReturningPatientCookie.resolve(request.getCookies())
                .map(ReturningPatientCookie::patient)
                .flatMap(IncomingPatientIdentifierResolver::maybeLong);
    }


}

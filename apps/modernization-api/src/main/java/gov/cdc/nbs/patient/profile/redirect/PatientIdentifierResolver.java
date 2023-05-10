package gov.cdc.nbs.patient.profile.redirect;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class PatientIdentifierResolver {

    private static final String PATIENT_PARENT_ID = "MPRUid";
    private static final String PATIENT_ID = "uid";

    public static Optional<String> fromQueryParams(final HttpServletRequest request) {
        String parent = request.getParameter(PATIENT_PARENT_ID);

        if (parent == null) {
            return Optional.ofNullable(request.getParameter(PATIENT_ID));
        }
        return Optional.of(parent);

    }

    public static Optional<String> fromReturningPatient(final HttpServletRequest request) {
        return ReturningPatientCookie.resolve(request.getCookies())
            .map(ReturningPatientCookie::patient);
    }

    private PatientIdentifierResolver() {
    }
}

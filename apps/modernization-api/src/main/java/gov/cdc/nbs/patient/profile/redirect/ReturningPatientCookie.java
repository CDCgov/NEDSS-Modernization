package gov.cdc.nbs.patient.profile.redirect;

import org.springframework.http.HttpHeaders;

import javax.servlet.http.Cookie;
import java.util.Optional;

public record ReturningPatientCookie(String patient) {

    private static final String RETURNING_PATIENT_COOKIE = "Returning-Patient";

    public static ReturningPatientCookie empty() {
        return new ReturningPatientCookie("");
    }

    public static Optional<ReturningPatientCookie> resolve(final Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(RETURNING_PATIENT_COOKIE)) {
                    String identifier = cookie.getValue();
                    return Optional.of(new ReturningPatientCookie(identifier));
                }
            }
        }
        return Optional.empty();
    }

    public ReturningPatientCookie(final long patient) {
        this(String.valueOf(patient));
    }

    public void apply(final HttpHeaders headers) {
        String value = RETURNING_PATIENT_COOKIE + "=" + patient + "; Path=/nbs/; SameSite=Strict; HttpOnly";
        headers.add(HttpHeaders.SET_COOKIE, value);
    }
}

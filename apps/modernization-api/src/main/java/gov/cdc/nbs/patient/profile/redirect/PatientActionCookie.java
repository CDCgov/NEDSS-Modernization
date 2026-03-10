package gov.cdc.nbs.patient.profile.redirect;

import gov.cdc.nbs.web.FindCookie;
import jakarta.servlet.http.Cookie;
import java.util.Optional;

public record PatientActionCookie(String name, String action) {

  private static final String COOKIE_NAME = "Patient-Action";
  private static final PatientActionCookie EMPTY = new PatientActionCookie("");

  public static Optional<PatientActionCookie> resolve(final Cookie[] cookies) {
    return FindCookie.in(COOKIE_NAME, cookies)
        .map(cookie -> new PatientActionCookie(COOKIE_NAME, cookie.getValue()));
  }

  public static PatientActionCookie empty() {
    return EMPTY;
  }

  public PatientActionCookie(String identifier) {
    this(COOKIE_NAME, identifier);
  }
}

package gov.cdc.nbs.patient.profile.redirect;

import gov.cdc.nbs.web.AddCookie;
import gov.cdc.nbs.web.FindCookie;
import gov.cdc.nbs.web.RemoveCookie;
import jakarta.servlet.http.Cookie;
import java.util.Optional;
import java.util.function.Consumer;
import org.springframework.http.HttpHeaders;

public record ReturningPatientCookie(String name, String patient) {

  private static final String COOKIE_NAME = "Return-Patient";
  private static final ReturningPatientCookie EMPTY = new ReturningPatientCookie("");

  public static Optional<ReturningPatientCookie> resolve(final Cookie[] cookies) {
    return FindCookie.in(COOKIE_NAME, cookies)
        .map(cookie -> new ReturningPatientCookie(COOKIE_NAME, cookie.getValue()));
  }

  public static ReturningPatientCookie empty() {
    return EMPTY;
  }

  public ReturningPatientCookie(String patient) {
    this(COOKIE_NAME, patient);
  }

  public ReturningPatientCookie(final long patient) {
    this(String.valueOf(patient));
  }

  public Consumer<HttpHeaders> apply() {
    return headers -> {
      if (patient().isBlank()) {
        RemoveCookie.from(name(), headers);
      } else {
        AddCookie.to(name(), patient(), headers);
      }
    };
  }
}

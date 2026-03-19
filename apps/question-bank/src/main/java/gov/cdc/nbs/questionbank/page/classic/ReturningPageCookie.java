package gov.cdc.nbs.questionbank.page.classic;

import gov.cdc.nbs.web.AddCookie;
import gov.cdc.nbs.web.FindCookie;
import gov.cdc.nbs.web.RemoveCookie;
import jakarta.servlet.http.Cookie;
import java.util.Optional;
import java.util.function.Consumer;
import org.springframework.http.HttpHeaders;

public record ReturningPageCookie(String page) {

  public ReturningPageCookie(long page) {
    this(String.valueOf(page));
  }

  private static final String COOKIE_NAME = "Return-Page";

  private static final ReturningPageCookie EMPTY = new ReturningPageCookie(null);

  public static Optional<ReturningPageCookie> resolve(final Cookie[] cookies) {
    return FindCookie.in(COOKIE_NAME, cookies)
        .map(cookie -> new ReturningPageCookie(cookie.getValue()));
  }

  public static ReturningPageCookie empty() {
    return EMPTY;
  }

  public Consumer<HttpHeaders> apply() {
    return headers -> {
      if (page() == null) {
        RemoveCookie.from(COOKIE_NAME, headers);
      } else {
        AddCookie.to(COOKIE_NAME, page(), headers);
      }
    };
  }
}

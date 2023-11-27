package gov.cdc.nbs.questionbank.page.classic;

import java.util.Optional;
import java.util.function.Consumer;
import javax.servlet.http.Cookie;
import org.springframework.http.HttpHeaders;
import gov.cdc.nbs.web.AddCookie;
import gov.cdc.nbs.web.FindCookie;
import gov.cdc.nbs.web.RemoveCookie;

public record ReturningPageCookie(String page) {
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

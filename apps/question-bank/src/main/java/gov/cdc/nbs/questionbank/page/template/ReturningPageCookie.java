package gov.cdc.nbs.questionbank.page.template;

import gov.cdc.nbs.web.FindCookie;

import javax.servlet.http.Cookie;
import java.util.Optional;

public record ReturningPageCookie(String name, String page) {

  private static final String COOKIE_NAME = "Return-Page";
  private static final ReturningPageCookie EMPTY = new ReturningPageCookie("");

  public static Optional<ReturningPageCookie> resolve(final Cookie[] cookies) {
    return FindCookie.in(COOKIE_NAME, cookies)
        .map(cookie -> new ReturningPageCookie(COOKIE_NAME, cookie.getValue()));
  }

  public static ReturningPageCookie empty() {
    return EMPTY;
  }

  public ReturningPageCookie(String identifier) {
    this(COOKIE_NAME, identifier);
  }


}

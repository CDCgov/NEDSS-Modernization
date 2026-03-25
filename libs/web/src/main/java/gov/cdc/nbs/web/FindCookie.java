package gov.cdc.nbs.web;

import jakarta.servlet.http.Cookie;
import java.util.Optional;

public class FindCookie {

  public static Optional<Cookie> in(final String name, final Cookie[] cookies) {
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(name)) {
          return Optional.of(cookie);
        }
      }
    }
    return Optional.empty();
  }

  private FindCookie() {}
}

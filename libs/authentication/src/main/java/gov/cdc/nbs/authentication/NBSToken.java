package gov.cdc.nbs.authentication;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

public record NBSToken(String value) {

  private static final String NBS_TOKEN_NAME = "nbs_token";

  public void apply(final SecurityProperties properties, final HttpServletResponse response) {
    Cookie cookie = asCookie(properties.getTokenCookieSecure());
    cookie.setMaxAge(properties.getTokenExpirationSeconds());
    response.addCookie(cookie);
  }

  public static Optional<NBSToken> resolve(Cookie[] cookies) {
    if (cookies == null) {
      return Optional.empty();
    }
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(NBS_TOKEN_NAME)) {
        return Optional.of(new NBSToken(cookie.getValue()));
      }
    }
    return Optional.empty();
  }

  @SuppressWarnings({"squid:S3330"})
  private Cookie asCookie(boolean secure) {
    Cookie cookie = new Cookie(NBS_TOKEN_NAME, value());
    cookie.setPath("/");
    cookie.setSecure(secure);
    return cookie;
  }
}

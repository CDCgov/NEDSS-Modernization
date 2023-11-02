package gov.cdc.nbs.authentication;

import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import gov.cdc.nbs.authentication.config.SecurityProperties;

public record NBSToken(String value) {

  private static final String NBS_TOKEN_NAME = "nbs_token";

  public void apply(
      final SecurityProperties properties,
      final HttpServletResponse response) {
    Cookie cookie = asCookie();
    cookie.setMaxAge(properties.getTokenExpirationSeconds());
    response.addCookie(cookie);
  }

  public static Optional<NBSToken> resolve(Cookie[] cookies) {
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(NBS_TOKEN_NAME)) {
        return Optional.of(new NBSToken(cookie.getValue()));
      }
    }
    return Optional.empty();
  }


  @SuppressWarnings({"squid:S3330"})
  private Cookie asCookie() {
    Cookie cookie = new Cookie(NBS_TOKEN_NAME, value());
    cookie.setPath("/");
    cookie.setSecure(true);
    return cookie;
  }

}

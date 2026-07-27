package gov.cdc.nbs.authentication;

import java.util.concurrent.TimeUnit;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "nbs.security")
public record SecurityProperties(
    String tokenSecret, String tokenIssuer, long tokenExpirationMillis, Boolean tokenCookieSecure) {
  public int getTokenExpirationSeconds() {
    return Math.toIntExact(TimeUnit.MILLISECONDS.toSeconds(tokenExpirationMillis));
  }

  public boolean getTokenCookieSecure() {
    return tokenCookieSecure == null || tokenCookieSecure == true;
  }
}

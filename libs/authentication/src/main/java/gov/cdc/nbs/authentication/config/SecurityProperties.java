package gov.cdc.nbs.authentication.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;


@ConfigurationProperties(prefix = "nbs.security")
public record SecurityProperties(
    String tokenSecret,
    String tokenIssuer,
    long tokenExpirationMillis
) {
  public int getTokenExpirationSeconds() {
    return Math.toIntExact(TimeUnit.MILLISECONDS.toSeconds(tokenExpirationMillis));
  }
}

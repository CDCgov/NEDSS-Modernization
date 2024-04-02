package gov.cdc.nbs.gateway.security.oidc;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public record AuthenticationService(URI uri, String base) {

  public String path(final String path) {
    return UriComponentsBuilder.fromPath(base)
        .path(path)
        .toUriString();
  }

}

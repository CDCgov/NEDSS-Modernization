package gov.cdc.nbs.gateway;

import java.net.URI;
import org.springframework.web.util.UriComponentsBuilder;

public class Service {

  private final URI uri;
  private final String base;

  public Service(final URI uri) {
    this(uri, "/");
  }

  public Service(final URI uri, final String base) {
    this.uri = uri;
    this.base = base;
  }

  public URI uri() {
    return uri;
  }

  public String base() {
    return base;
  }

  public String path(final String path) {
    return UriComponentsBuilder.fromPath(base).path(path).toUriString();
  }
}

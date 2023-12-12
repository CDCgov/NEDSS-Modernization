package gov.cdc.nbs.gateway.pagebuilder;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public record PageBuilderService(URI uri, String base) {

  public String path(final String path) {
    return UriComponentsBuilder.fromPath(base)
        .path(path)
        .toUriString();
  }
}

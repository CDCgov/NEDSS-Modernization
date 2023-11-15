package gov.cdc.nbs.questionbank.page.template;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
class ClassicManagePageRequester {

  private static final String LOCATION = "/ManagePage.do";

  private final RestTemplate template;

  ClassicManagePageRequester(
      @Qualifier("classic") final RestTemplate template
  ) {
    this.template = template;
  }

  void request() {
    String pageLocation = UriComponentsBuilder.fromPath(LOCATION)
        .queryParam("method", "list")
        .queryParam("initLoad", true)
        .build()
        .toUriString();

    RequestEntity<Void> viewPageRequest = RequestEntity
        .get(pageLocation)
        .build();

    this.template.exchange(viewPageRequest, Void.class);

  }
}

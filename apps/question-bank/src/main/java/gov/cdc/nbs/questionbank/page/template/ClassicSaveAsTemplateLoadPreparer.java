package gov.cdc.nbs.questionbank.page.template;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
class ClassicSaveAsTemplateLoadPreparer {

  private static final String LOCATION = "/ManagePage.do";

  private final RestTemplate template;

  ClassicSaveAsTemplateLoadPreparer(
      @Qualifier("classic") final RestTemplate template
  ) {
    this.template = template;
  }

  void prepare() {
    String pageLocation = UriComponentsBuilder.fromPath(LOCATION)
        .queryParam("method", "saveAsTemplateLoad")
        .build()
        .toUriString();

    RequestEntity<Void> viewPageRequest = RequestEntity
        .get(pageLocation)
        .build();

    this.template.exchange(viewPageRequest, Void.class);

  }
}

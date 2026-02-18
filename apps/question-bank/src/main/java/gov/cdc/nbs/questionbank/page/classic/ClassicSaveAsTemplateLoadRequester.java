package gov.cdc.nbs.questionbank.page.classic;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
class ClassicSaveAsTemplateLoadRequester {

  private static final String LOCATION = "/ManagePage.do";

  private final RestTemplate template;

  ClassicSaveAsTemplateLoadRequester(@Qualifier("classicTemplate") final RestTemplate template) {
    this.template = template;
  }

  void request() {
    String pageLocation =
        UriComponentsBuilder.fromPath(LOCATION)
            .queryParam("method", "saveAsTemplateLoad")
            .build()
            .toUriString();

    RequestEntity<Void> viewPageRequest = RequestEntity.get(pageLocation).build();

    this.template.exchange(viewPageRequest, Void.class);
  }
}

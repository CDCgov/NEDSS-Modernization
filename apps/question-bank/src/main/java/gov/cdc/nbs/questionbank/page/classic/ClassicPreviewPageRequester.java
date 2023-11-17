package gov.cdc.nbs.questionbank.page.classic;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
class ClassicPreviewPageRequester {

  private static final String LOCATION = "/PreviewPage.do";

  private final RestTemplate template;

  ClassicPreviewPageRequester(
      @Qualifier("classic") final RestTemplate template) {
    this.template = template;
  }

  void request(final long page) {

    String pageLocation = UriComponentsBuilder.fromPath(LOCATION)
        .queryParam("method", "viewPageLoad")
        .queryParam("waTemplateUid", page)
        .build()
        .toUriString();

    RequestEntity<Void> viewPageRequest = RequestEntity
        .get(pageLocation)
        .build();

    this.template.exchange(viewPageRequest, Void.class);

  }
}

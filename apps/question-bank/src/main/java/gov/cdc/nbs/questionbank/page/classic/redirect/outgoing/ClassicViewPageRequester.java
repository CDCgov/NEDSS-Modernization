package gov.cdc.nbs.questionbank.page.classic.redirect.outgoing;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ClassicViewPageRequester {
  private static final String LOCATION = "/PreviewPage.do";

  private final RestTemplate template;

  public ClassicViewPageRequester(@Qualifier("classicTemplate") final RestTemplate template) {
    this.template = template;
  }

  public void request(long page) {
    String pageLocation =
        UriComponentsBuilder.fromPath(LOCATION)
            .queryParam("from", "L")
            .queryParam("waTemplateUid", page)
            .queryParam("method", "viewPageLoad")
            .build()
            .toUriString();

    RequestEntity<Void> viewPageRequest = RequestEntity.get(pageLocation).build();

    this.template.exchange(viewPageRequest, Void.class);
  }
}

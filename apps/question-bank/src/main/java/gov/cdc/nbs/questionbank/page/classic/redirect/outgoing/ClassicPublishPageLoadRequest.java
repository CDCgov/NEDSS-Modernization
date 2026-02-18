package gov.cdc.nbs.questionbank.page.classic.redirect.outgoing;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ClassicPublishPageLoadRequest {
  private static final String LOCATION = "/ManagePage.do";

  private final RestTemplate template;

  public ClassicPublishPageLoadRequest(@Qualifier("classicTemplate") final RestTemplate template) {
    this.template = template;
  }

  public void request() {

    String pageLocation =
        UriComponentsBuilder.fromPath(LOCATION)
            .queryParam("method", "publishPopUpLoad")
            .build()
            .toUriString();

    RequestEntity<Void> publishPageRequest = RequestEntity.post(pageLocation).build();

    this.template.exchange(publishPageRequest, Void.class);
  }
}

package gov.cdc.nbs.questionbank.page.classic.redirect.outgoing;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ClassicPublishPageRequester {

  private static final String LOCATION = "/ManagePage.do";

  private final RestTemplate template;

  public ClassicPublishPageRequester(@Qualifier("classicTemplate") final RestTemplate template) {
    this.template = template;
  }

  public void request(final String versionNotes) {

    MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
    data.add("selection.versionNote", versionNotes);

    String pageLocation =
        UriComponentsBuilder.fromPath(LOCATION)
            .queryParam("method", "publishPage")
            .build()
            .toUriString();

    RequestEntity<MultiValueMap<String, String>> publishPageRequest =
        RequestEntity.post(pageLocation)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(data);

    this.template.exchange(publishPageRequest, Void.class);
  }
}

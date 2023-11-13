package gov.cdc.nbs.questionbank.page.template;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
class ClassicPageTemplateCreator {

  private final RestTemplate template;
  private final ClassicViewPagePreparer preparer;

  ClassicPageTemplateCreator(
      @Qualifier("classic") final RestTemplate template,
      final ClassicViewPagePreparer preparer
  ) {
    this.template = template;
    this.preparer = preparer;
  }

  void create(final long page, final CreateTemplateRequest request) {

    this.preparer.prepare(page);

    MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();

    form.set("selection.templateNm", request.name());
    form.set("selection.descTxt", request.description());

    String location = UriComponentsBuilder.fromPath("/ManagePage.do")
        .queryParam("method", "saveAsTemplate")
        .build()
        .toUriString();

    RequestEntity<MultiValueMap<String, Object>> classicRequest = RequestEntity
        .post(location)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(form);

    this.template.exchange(classicRequest, Void.class);
  }

}

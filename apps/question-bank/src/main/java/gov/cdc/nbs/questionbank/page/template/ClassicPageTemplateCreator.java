package gov.cdc.nbs.questionbank.page.template;

import gov.cdc.nbs.questionbank.page.classic.ClassicCreateTemplatePreparer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
class ClassicPageTemplateCreator {

  private final RestTemplate template;
  private final ClassicCreateTemplatePreparer preparer;

  ClassicPageTemplateCreator(
      @Qualifier("classicTemplate") final RestTemplate template,
      final ClassicCreateTemplatePreparer preparer) {
    this.template = template;
    this.preparer = preparer;
  }

  void create(final long page, final CreateTemplateRequest request) {

    this.preparer.prepare(page);

    MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();

    form.add("selection.templateNm", request.name());
    form.add("selection.descTxt", request.description());

    String location =
        UriComponentsBuilder.fromPath("/ManagePage.do")
            .queryParam("method", "saveAsTemplate")
            .build()
            .toUriString();

    HttpHeaders header = new HttpHeaders();
    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(form, header);
    this.template.postForEntity(location, entity, Void.class);
  }
}

package gov.cdc.nbs.questionbank.page.template;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class ClassicViewPagePreparerTest {

  @Test
  void should_prepare_the_classic_view_page() {

    RestTemplate template = new RestTemplate();

    ClassicPreviewPagePreparer preparer = new ClassicPreviewPagePreparer(template);

    MockRestServiceServer server = MockRestServiceServer.bindTo(template)
        .build();

    server.expect(requestTo("/PreviewPage.do?method=viewPageLoad&waTemplateUid=2027"))
        .andExpect(method(HttpMethod.GET))
        .andExpect(queryParam("waTemplateUid", "2027"))
        .andExpect(queryParam("method", "viewPageLoad"))
        .andRespond(withSuccess())
    ;

    preparer.prepare(2027L);

    server.verify();

  }
}

package gov.cdc.nbs.questionbank.page.classic.redirect.outgoing;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

class ClassicViewPageRequesterTest {
  @Test
  void should_call_manage_page() {

    RestTemplate template = new RestTemplate();

    ClassicViewPageRequester request = new ClassicViewPageRequester(template);

    MockRestServiceServer server = MockRestServiceServer.bindTo(template).build();

    server
        .expect(requestTo("/PreviewPage.do?from=L&waTemplateUid=13&method=viewPageLoad"))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    request.request(13l);

    server.verify();
  }
}

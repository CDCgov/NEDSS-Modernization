package gov.cdc.nbs.questionbank.page.classic;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicManagePageRequester;

class ClassicManagePageRequesterTest {
  @Test
  void should_prepare_the_pre() {

    RestTemplate template = new RestTemplate();

    ClassicManagePageRequester request = new ClassicManagePageRequester(template);

    MockRestServiceServer server = MockRestServiceServer.bindTo(template)
        .build();

    server.expect(requestTo("/ManagePage.do?method=list&initLoad=true"))
        .andExpect(method(HttpMethod.GET))
        .andExpect(queryParam("method", "list"))
        .andExpect(queryParam("initLoad", "true"))
        .andRespond(withSuccess());

    request.request();

    server.verify();

  }
}

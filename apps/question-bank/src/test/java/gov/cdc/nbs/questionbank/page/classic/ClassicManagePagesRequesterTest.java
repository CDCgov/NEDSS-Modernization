package gov.cdc.nbs.questionbank.page.classic;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicManagePagesRequester;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

class ClassicManagePagesRequesterTest {

  @Test
  void should_call_manage_page() {

    RestTemplate template = new RestTemplate();

    ClassicManagePagesRequester request = new ClassicManagePagesRequester(template);

    MockRestServiceServer server = MockRestServiceServer.bindTo(template).build();

    server
        .expect(requestTo("/ManagePage.do?method=list&initLoad=true"))
        .andExpect(method(HttpMethod.GET))
        .andExpect(queryParam("method", "list"))
        .andExpect(queryParam("initLoad", "true"))
        .andRespond(withSuccess());

    request.request();

    server.verify();
  }
}

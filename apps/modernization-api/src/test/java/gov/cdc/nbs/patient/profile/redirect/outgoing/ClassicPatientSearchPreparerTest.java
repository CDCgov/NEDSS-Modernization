package gov.cdc.nbs.patient.profile.redirect.outgoing;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

class ClassicPatientSearchPreparerTest {

  @Test
  void should_prepare_the_classic_search_results() {

    RestTemplate template = new RestTemplate();

    ClassicPatientSearchPreparer preparer = new ClassicPatientSearchPreparer(template);

    MockRestServiceServer server = MockRestServiceServer.bindTo(template).build();

    server
        .expect(requestTo("/HomePage.do?method=patientSearchSubmit"))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    preparer.prepare();

    server.verify();
  }
}

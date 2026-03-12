package gov.cdc.nbs.patient.profile.redirect.outgoing;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

class ClassicPatientProfilePreparerTest {

  @Test
  void should_prepare_the_classic_profile_for_the_patient() {

    RestTemplate template = new RestTemplate();

    ClassicPatientProfilePreparer preparer = new ClassicPatientProfilePreparer(template);

    MockRestServiceServer server = MockRestServiceServer.bindTo(template).build();

    server
        .expect(requestTo("/PatientSearchResults1.do?ContextAction=ViewFile&uid=5993"))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    preparer.prepare(5993L);

    server.verify();
  }
}

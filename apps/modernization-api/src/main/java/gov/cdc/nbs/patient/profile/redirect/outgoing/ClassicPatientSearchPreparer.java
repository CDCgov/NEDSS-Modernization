package gov.cdc.nbs.patient.profile.redirect.outgoing;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ClassicPatientSearchPreparer {

  private static final String LOCATION = "/HomePage.do?method=patientSearchSubmit";
  private final RestTemplate template;

  ClassicPatientSearchPreparer(@Qualifier("classicTemplate") final RestTemplate classic) {
    this.template = classic;
  }

  /** Issues a request to the NBS6 home page search on behalf of the current user. */
  public void prepare() {

    RequestEntity<Void> request = RequestEntity.get(LOCATION).build();

    this.template.exchange(request, Void.class);
  }
}

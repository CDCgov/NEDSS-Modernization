package gov.cdc.nbs.patient.profile.redirect.outgoing;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ClassicPatientSearchPreparer {

    private static final String PATH = "/HomePage.do?method=patientSearchSubmit";
    private final RestTemplate template;

    public ClassicPatientSearchPreparer(
        @Qualifier("classic") final RestTemplate template
    ) {
        this.template = template;
    }

    public void prepare() {

        RequestEntity<Void> request = RequestEntity
            .get(PATH)
            .build();

        this.template.exchange(request, Void.class);

    }

}

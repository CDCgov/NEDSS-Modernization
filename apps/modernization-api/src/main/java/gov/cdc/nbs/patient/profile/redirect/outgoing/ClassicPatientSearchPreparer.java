package gov.cdc.nbs.patient.profile.redirect.outgoing;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
class ClassicPatientSearchPreparer {

    private static final String LOCATION = "/HomePage.do?method=patientSearchSubmit";
    private final RestTemplate template;

    ClassicPatientSearchPreparer(
        @Qualifier("classic") final RestTemplate template
    ) {
        this.template = template;
    }

    void prepare() {

        RequestEntity<Void> request = RequestEntity
            .get(LOCATION)
            .build();

        this.template.exchange(request, Void.class);

    }

}

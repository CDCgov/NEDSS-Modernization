package gov.cdc.nbs.gateway.patient.profile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
class PatientProfileServiceProvider {

    @Bean
    PatientProfileService patientProfileService(
        @Value("${nbs.gateway.patient.profile.protocol:${nbs.gateway.defaults.protocol}}") final String protocol,
        @Value("${nbs.gateway.patient.profile.service}") final String service
    ) throws URISyntaxException {
        URI uri = new URI(protocol + "://" + service);

        return new PatientProfileService(uri);
    }

}

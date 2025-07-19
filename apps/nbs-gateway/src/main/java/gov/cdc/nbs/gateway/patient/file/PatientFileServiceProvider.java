package gov.cdc.nbs.gateway.patient.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
class PatientFileServiceProvider {

    @Bean
    PatientFileService patientFileService(
        @Value("${nbs.gateway.patient.file.protocol:${nbs.gateway.defaults.protocol}}") final String protocol,
        @Value("${nbs.gateway.patient.file.service}") final String service
    ) throws URISyntaxException {
        URI uri = new URI(protocol + "://" + service);

        return new PatientFileService(uri);
    }

}

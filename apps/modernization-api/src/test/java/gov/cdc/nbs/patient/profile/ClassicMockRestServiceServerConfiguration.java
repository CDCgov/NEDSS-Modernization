package gov.cdc.nbs.patient.profile;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

/**
 * A {@code Configuration} active only for testing that will make a {@link MockRestServiceServer} available that is
 * associated with the Classic {@link RestTemplate}.
 */
@TestConfiguration
class ClassicMockRestServiceServerConfiguration {

    @Bean
    @Qualifier("classic")
    MockRestServiceServer classicMockRestServiceServer(@Qualifier("classic") final RestTemplate template) {
        return MockRestServiceServer.bindTo(template)
            .build();
    }
}

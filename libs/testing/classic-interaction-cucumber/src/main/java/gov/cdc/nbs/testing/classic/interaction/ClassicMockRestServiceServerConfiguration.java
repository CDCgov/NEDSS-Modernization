package gov.cdc.nbs.testing.classic.interaction;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

/**
 * A {@code Configuration} active only for testing that will make a {@link MockRestServiceServer} available that is
 * associated with the Classic {@link RestTemplate}.
 */
@Configuration
class ClassicMockRestServiceServerConfiguration {

    @Bean
    @Qualifier("classic")
    MockRestServiceServer classicMockRestServiceServer(@Qualifier("classic") final RestTemplate template) {
        return MockRestServiceServer.bindTo(template)
            .build();
    }
}

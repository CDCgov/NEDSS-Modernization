package gov.cdc.nbs.authorization;

import gov.cdc.nbs.support.TestActive;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
class TestActiveUserDetailsConfiguration {

    @Bean
    static TestActive<UserDetails> userDetailsTestActive() {
        return new TestActive<>();
    }

}

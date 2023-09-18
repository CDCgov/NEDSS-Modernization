package gov.cdc.nbs.authorization;

import gov.cdc.nbs.authentication.SessionCookie;
import gov.cdc.nbs.support.TestActive;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TestActiveSessionCookieConfiguration {

    @Bean
    static TestActive<SessionCookie> sessionCookieTestActive() {
        return new TestActive<>();
    }

}

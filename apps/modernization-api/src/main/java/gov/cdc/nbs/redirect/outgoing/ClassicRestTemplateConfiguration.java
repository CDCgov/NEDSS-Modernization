package gov.cdc.nbs.redirect.outgoing;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;

@Configuration
class ClassicRestTemplateConfiguration {

    @Bean
    @Qualifier("classic")
    RestTemplate classicRestTemplate(
        final ClassicPathResolver resolver,
        final ClassicOutgoingRequestInterceptor interceptor
    ) throws MalformedURLException {

        return new RestTemplateBuilder()
            .rootUri(resolver.base().toURL().toString())
            .additionalInterceptors(new ClassicOutgoingLoggingHttpRequestInterceptor(), interceptor)
            .build();
    }

}

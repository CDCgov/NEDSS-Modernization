package gov.cdc.nbs.redirect.outgoing;

import java.net.MalformedURLException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class ClassicRestTemplateConfiguration {

  @Bean(name = "classicTemplate")
  RestTemplate classicTemplate(
      final ClassicPathResolver resolver, final ClassicOutgoingRequestInterceptor interceptor)
      throws MalformedURLException {

    return new RestTemplateBuilder()
        .rootUri(resolver.base().toURL().toString())
        .additionalInterceptors(new ClassicOutgoingLoggingHttpRequestInterceptor(), interceptor)
        .build();
  }
}

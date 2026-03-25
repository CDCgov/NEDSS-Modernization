package gov.cdc.nbs.gateway.logo;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@ConditionalOnProperty(prefix = "nbs.gateway.logo", name = "file")
class LogoEndpoint {

  @Bean
  RouterFunction<ServerResponse> logo(final LogoSettings settings) {
    FileSystemResource logo = new FileSystemResource(settings.file());
    return route(
        GET(settings.path()).or(GET(settings.resource())), request -> ok().bodyValue(logo));
  }
}

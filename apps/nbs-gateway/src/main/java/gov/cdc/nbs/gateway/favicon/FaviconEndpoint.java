package gov.cdc.nbs.gateway.favicon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
class FaviconEndpoint {

  @Bean
  RouterFunction<ServerResponse> favicon(
      @Value("classpath:favicon.ico") Resource favicon
  ) {
    return route(GET("/favicon.ico"), request -> ok().bodyValue(favicon));
  }

}

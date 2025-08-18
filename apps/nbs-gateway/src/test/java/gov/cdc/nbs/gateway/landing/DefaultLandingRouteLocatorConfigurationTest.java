package gov.cdc.nbs.gateway.landing;

import gov.cdc.nbs.gateway.GatewayApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
    classes = {GatewayApplication.class, LandingServiceProvider.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class DefaultLandingRouteLocatorConfigurationTest {

  @Autowired
  WebTestClient webClient;

  @Test
  void should_redirect_to_default_landing_when_landing_not_configured() {
    webClient
        .get().uri(
            builder -> builder
                .path("/")
                .build())
        .exchange()
        .expectHeader().location("/nbs/login")
        .expectStatus().is3xxRedirection();
  }

}

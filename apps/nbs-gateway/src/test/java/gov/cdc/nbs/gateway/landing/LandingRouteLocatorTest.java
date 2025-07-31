package gov.cdc.nbs.gateway.landing;

import gov.cdc.nbs.gateway.GatewayApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
    classes = {GatewayApplication.class, LandingServiceProvider.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "nbs.gateway.landing.base=/welcome"
    })
@Import(LandingServiceProvider.class)
class LandingRouteLocatorTest {


  @Autowired
  WebTestClient webClient;

  @Test
  void should_redirect_to_welcome() {
    webClient
        .get().uri(
            builder -> builder
                .path("/")
                .build())
        .exchange()
        .expectHeader().location("/welcome")
        .expectStatus().is3xxRedirection();
  }

}

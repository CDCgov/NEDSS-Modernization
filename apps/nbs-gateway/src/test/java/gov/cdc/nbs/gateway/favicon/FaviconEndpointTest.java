package gov.cdc.nbs.gateway.favicon;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class FaviconEndpointTest {

  @Autowired
  WebTestClient webClient;

  @Test
  void should_route_to_favicon() {

    webClient
        .get().uri("/favicon.ico")
        .exchange()
        .expectStatus()
        .isOk();
  }

}

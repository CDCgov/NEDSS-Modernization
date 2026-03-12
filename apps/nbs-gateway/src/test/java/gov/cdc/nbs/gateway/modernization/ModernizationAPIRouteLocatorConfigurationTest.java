package gov.cdc.nbs.gateway.modernization;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {"nbs.gateway.modernization.service=localhost:10001"})
class ModernizationAPIRouteLocatorConfigurationTest {

  @RegisterExtension
  static WireMockExtension service =
      WireMockExtension.newInstance().options(wireMockConfig().port(10001)).build();

  @Autowired WebTestClient webClient;

  @Test
  void should_route_api_requests_to_the_modernization_service() {
    service.stubFor(get(urlEqualTo("/nbs/api/me")).willReturn(ok()));

    webClient
        .get()
        .uri(builder -> builder.path("/nbs/api/me").build())
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  void should_route_encryption_requests_to_the_modernization_service() {
    service.stubFor(get(urlEqualTo("/encryption/encrypt")).willReturn(ok()));

    webClient
        .get()
        .uri(builder -> builder.path("/encryption/encrypt").build())
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  void should_route_graphql_requests_to_the_modernization_service() {
    service.stubFor(post(urlPathMatching("/graphql")).willReturn(ok()));

    webClient
        .post()
        .uri(builder -> builder.path("/graphql").build())
        .exchange()
        .expectStatus()
        .isOk();
  }
}

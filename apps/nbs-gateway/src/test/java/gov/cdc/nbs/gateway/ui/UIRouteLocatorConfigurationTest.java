package gov.cdc.nbs.gateway.ui;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {"nbs.gateway.ui.service=localhost:10001"})
class UIRouteLocatorConfigurationTest {

  @RegisterExtension
  static WireMockExtension service =
      WireMockExtension.newInstance().options(wireMockConfig().port(10001)).build();

  @Autowired WebTestClient webClient;

  @Test
  void should_route_requests_to_the_ui_service_that_are_not_handled_by_other_routes() {
    service.stubFor(get(urlEqualTo("/ui/catch.all")).willReturn(ok()));

    webClient
        .get()
        .uri(builder -> builder.path("/ui/catch.all").build())
        .exchange()
        .expectStatus()
        .isOk();
  }
}

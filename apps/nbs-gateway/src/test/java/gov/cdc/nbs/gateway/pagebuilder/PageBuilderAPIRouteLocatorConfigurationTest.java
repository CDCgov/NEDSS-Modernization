package gov.cdc.nbs.gateway.pagebuilder;

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
    properties = {"nbs.gateway.pagebuilder.service=localhost:10001"})
class PageBuilderAPIRouteLocatorConfigurationTest {

  @RegisterExtension
  static WireMockExtension service =
      WireMockExtension.newInstance().options(wireMockConfig().port(10001)).build();

  @Autowired WebTestClient webClient;

  @Test
  void should_route_api_requests_to_the_page_builder_service() {
    service.stubFor(get(urlEqualTo("/nbs/page-builder/api/something")).willReturn(ok()));

    webClient
        .get()
        .uri(builder -> builder.path("/nbs/page-builder/api/something").build())
        .exchange()
        .expectStatus()
        .isOk();
  }
}

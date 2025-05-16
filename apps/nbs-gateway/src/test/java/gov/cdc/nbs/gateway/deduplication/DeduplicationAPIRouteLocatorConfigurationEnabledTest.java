package gov.cdc.nbs.gateway.deduplication;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
    "nbs.gateway.deduplication.service=localhost:10002",
    "nbs.gateway.deduplication.enabled=true"
})
class DeduplicationAPIRouteLocatorConfigurationEnabledTest {

  @RegisterExtension
  static WireMockExtension deduplication = WireMockExtension.newInstance()
      .options(wireMockConfig().port(10002))
      .build();

  @Autowired
  WebTestClient webClient;

  @Autowired
  RouteLocator deduplicationConfig;

  @Autowired
  DeduplicationService service;

  @Test
  void should_autowire() {
    assertThat(service.base()).isEqualTo("/");
    assertThat(service.uri().getHost()).isEqualTo("localhost");
    assertThat(service.uri().getPort()).isEqualTo(10002);
  }

  @Test
  void should_route() {
    deduplication.stubFor(get(urlPathMatching("/nbs/api/deduplication/")).willReturn(ok()));

    webClient
        .get().uri(
            builder -> builder
                .path("/nbs/api/deduplication/")
                .build())
        .exchange()
        .expectStatus()
        .isOk();
  }
}

package gov.cdc.nbs.gateway.deduplication;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.notFound;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
    "nbs.gateway.modernization.service=localhost:10001",
    "nbs.gateway.deduplication.service=localhost:10002",
    "nbs.gateway.deduplication.enabled=false"
})
class DeduplicationAPIRouteLocatorConfigurationDisabledTest {

  @RegisterExtension
  static WireMockExtension modernized = WireMockExtension.newInstance()
      .options(wireMockConfig().port(10001))
      .build();

  @RegisterExtension
  static WireMockExtension deduplication = WireMockExtension.newInstance()
      .options(wireMockConfig().port(10002))
      .build();

  @Autowired
  WebTestClient webClient;

  @Test
  void should_not_route() {
    // API falls back to modernized api when deduplication is disabled
    modernized.stubFor(get(urlEqualTo("/nbs/api/deduplication/")).willReturn(notFound()));
    deduplication.stubFor(get(urlPathMatching("/nbs/api/deduplication/")).willReturn(ok()));

    webClient
        .get().uri(
            builder -> builder
                .path("/nbs/api/deduplication/")
                .build())
        .exchange()
        .expectStatus()
        .isNotFound();
  }
}

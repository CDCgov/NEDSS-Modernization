package gov.cdc.nbs.gateway.deduplication;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
    "nbs.gateway.deduplication.merge.enabled=false",
    "nbs.gateway.classic=http://localhost:10000"
})
class MergeLandingPageRouteLocatorConfigurationDisabledTest {

  @RegisterExtension
  static WireMockExtension classic = WireMockExtension.newInstance()
      .options(wireMockConfig().port(10000))
      .build();

  @Autowired
  WebTestClient webClient;

  @Test
  void should_not_route_to_deduplication_merge_landing_page_when_disabled() {
    classic.stubFor(
        get(urlEqualTo("/nbs/LoadMergeCandidateList2.do?ContextAction=GlobalMP_SystemIndentified")).willReturn(ok()));

    webClient
        .get().uri(
            builder -> builder
                .path("/nbs/LoadMergeCandidateList2.do")
                .queryParam("ContextAction",
                    "GlobalMP_SystemIndentified")
                .build())
        .exchange()
        .expectStatus()
        .isOk();
  }
}

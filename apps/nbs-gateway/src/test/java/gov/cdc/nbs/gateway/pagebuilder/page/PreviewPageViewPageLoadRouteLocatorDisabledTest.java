package gov.cdc.nbs.gateway.pagebuilder.page;

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
    properties = {
      "nbs.gateway.classic=http://localhost:10000",
      "nbs.gateway.pagebuilder.service=localhost:10002",
      "nbs.gateway.pagebuilder.enabled=false"
    })
class PreviewPageViewPageLoadRouteLocatorDisabledTest {

  @RegisterExtension
  static WireMockExtension classic =
      WireMockExtension.newInstance().options(wireMockConfig().port(10000)).build();

  @RegisterExtension
  static WireMockExtension pageBuilderApi =
      WireMockExtension.newInstance().options(wireMockConfig().port(10002)).build();

  @Autowired WebTestClient webClient;

  @Test
  void should_route_to_classic_when_preview_page_route_is_disabled() {

    classic.stubFor(get(urlPathMatching("/nbs/PreviewPage.do\\\\?.*")).willReturn(ok()));

    webClient
        .get()
        .uri(
            builder ->
                builder.path("/nbs/PreviewPage.do").queryParam("method", "viewPageLoad").build())
        .cookie("Return-Page", "3119")
        .exchange()
        .expectStatus()
        .isOk();
  }
}

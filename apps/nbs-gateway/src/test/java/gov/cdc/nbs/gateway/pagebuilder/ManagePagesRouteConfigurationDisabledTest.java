package gov.cdc.nbs.gateway.pagebuilder;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
      "nbs.gateway.classic=http://localhost:10000",
      "nbs.gateway.pagebuilder.service=localhost:10002",
      "nbs.gateway.pagebuilder.enabled=false"
    })
class ManagePagesRouteConfigurationDisabledTest {

  @RegisterExtension
  static WireMockExtension classic =
      WireMockExtension.newInstance().options(wireMockConfig().port(10000)).build();

  @RegisterExtension
  static WireMockExtension pagebuilderApi =
      WireMockExtension.newInstance().options(wireMockConfig().port(10002)).build();

  @Autowired WebTestClient webClient;

  @Autowired RouteLocator pagebuilderManagePagesConfig;

  @Autowired PageBuilderService service;

  @Test
  void should_not_route_to_modernized() {
    pagebuilderApi.stubFor(
        get(urlPathMatching("/nbs/redirect/pagebuilder/pages")).willReturn(ok()));

    webClient
        .get()
        .uri(builder -> builder.path("/nbs/ManagePage.do").queryParam("method", "list").build())
        .exchange()
        .expectStatus()
        .isNotFound();
  }
}

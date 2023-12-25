package gov.cdc.nbs.gateway.pagebuilder.page;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "nbs.gateway.classic=http://localhost:10000",
        "nbs.gateway.pagebuilder.service=localhost:10002",
        "nbs.gateway.pagebuilder.enabled=true",
        "nbs.gateway.pagebuilder.page.management.enabled=true"
    })
class ManagePageViewPageDetailsRouteLocatorConfigurationTest {

  @RegisterExtension
  static WireMockExtension classic = WireMockExtension.newInstance()
      .options(wireMockConfig().port(10000))
      .build();

  @RegisterExtension
  static WireMockExtension pageBuilderApi = WireMockExtension.newInstance()
      .options(wireMockConfig().port(10002))
      .build();

  @Autowired
  WebTestClient webClient;

  @Test
  void should_route_to_page_builder_redirect() {

    pageBuilderApi.stubFor(get(urlPathMatching("/nbs/page-builder/api/v1/pages/return"))
        .willReturn(ok()));

    webClient
        .get()
        .uri(
            builder -> builder
                .path("/nbs/ManagePage.do")
                .queryParam("method", "viewPageDetailsLoad")
                .build())
        .cookie("Return-Page", "3119")
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  void should_not_route_to_page_builder_redirect_when_return_cookie_is_not_present() {

    classic.stubFor(get(urlPathMatching("/nbs/ManagePage.do\\\\?.*")).willReturn(ok()));


    webClient
        .get()
        .uri(
            builder -> builder
                .path("/nbs/ManagePage.do")
                .queryParam("method", "viewPageDetailsLoad")
                .build())
        .exchange()
        .expectStatus()
        .isOk();
  }
}

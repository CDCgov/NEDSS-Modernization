package gov.cdc.nbs.gateway.pagebuilder;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.notFound;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
      "nbs.gateway.pagebuilder.enabled=true",
      "nbs.gateway.pagebuilder.page.management.edit.enabled=true"
    })
class EditPageRouteConfigurationTest {

  @RegisterExtension
  static WireMockExtension classic =
      WireMockExtension.newInstance().options(wireMockConfig().port(10000)).build();

  @RegisterExtension
  static WireMockExtension pageBuilderApi =
      WireMockExtension.newInstance().options(wireMockConfig().port(10002)).build();

  @Autowired WebTestClient webClient;

  @Autowired RouteLocator pagebuilderManagePagesConfig;

  @Autowired PageBuilderService service;

  @Test
  void should_contain_route() {
    assertNotNull(pagebuilderManagePagesConfig);
    assertTrue(
        pagebuilderManagePagesConfig
            .getRoutes()
            .any(i -> i.getId().equals("pagebuilder-edit-page") && i.getUri().equals(service.uri()))
            .block());
  }

  @Test
  void should_route_to_page_builder() {

    pageBuilderApi.stubFor(
        get(urlPathMatching("/nbs/page-builder/api/v1/pages/return")).willReturn(ok()));

    webClient
        .get()
        .uri(
            builder ->
                builder
                    .path("/nbs/ManagePage.do")
                    .queryParam("method", "editPageContentsLoad")
                    .build())
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  void should_not_route_without_method_param() {
    classic.stubFor(get(urlPathMatching("/nbs/ManagePage.do")).willReturn(notFound()));

    webClient
        .get()
        .uri(builder -> builder.path("/nbs/ManagePage.do").build())
        .exchange()
        .expectStatus()
        .isNotFound();
  }
}

package gov.cdc.nbs.gateway.report;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
      "nbs.gateway.classic=http://localhost:10000",
      "nbs.gateway.modernization.service=localhost:10001",
      "nbs.gateway.report.execution.enabled=true"
    })
class ReportAdminRouteLocatorConfigurationTest {

  @RegisterExtension
  static WireMockExtension classic =
      WireMockExtension.newInstance().options(wireMockConfig().port(10000)).build();

  @RegisterExtension
  static WireMockExtension modApi =
      WireMockExtension.newInstance().options(wireMockConfig().port(10001)).build();

  @Autowired WebTestClient webClient;

  @LocalServerPort int gatewayServerPort;

  @Test
  void should_route_modernization_ui_for_view() {
    modApi.stubFor(
        get(urlPathMatching("/nbs/redirect/report/management/configuration/2/1")).willReturn(ok()));

    webClient
        .post()
        .uri(builder -> builder.path("/nbs/ViewReport.do?report_uid=2&data_source_uid=1").build())
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .exchange()
        .expectStatus();
  }

  @Test
  void should_not_route_to_modernization_view_when_missing_params() {
    classic.stubFor(get("/nbs/ViewReport.do").willReturn(ok()));

    webClient
        .post()
        .uri(builder -> builder.path("/nbs/ViewReport.do?data_source_uid=1").build())
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .exchange()
        .expectStatus();
  }

  @Test
  void should_route_modernization_ui_for_add() {
    modApi.stubFor(
        get(urlPathMatching("/nbs/redirect/report/management/configuration/add")).willReturn(ok()));

    webClient
        .post()
        .uri(builder -> builder.path("/nbs/NewReport.do").build())
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .exchange()
        .expectStatus();
  }
}

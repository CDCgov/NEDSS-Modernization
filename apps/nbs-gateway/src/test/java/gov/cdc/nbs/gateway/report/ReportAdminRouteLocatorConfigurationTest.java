package gov.cdc.nbs.gateway.report;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.notFound;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
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
        post(urlPathMatching("/nbs/redirect/report/management/configuration/2/1"))
            .willReturn(ok()));

    webClient
        .post()
        .uri(
            builder ->
                builder.path("/nbs/ViewReport.do").query("report_uid=2&data_source_uid=1").build())
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  void should_not_route_to_modernization_view_when_missing_params() {
    classic.stubFor(post(urlPathMatching("/nbs/ViewReport.do.*")).willReturn(ok()));

    webClient
        .post()
        .uri(
            builder ->
                builder.path("/nbs/ViewReport.do").queryParam("data_source_uid", "1").build())
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  void should_route_modernization_ui_for_add() {
    modApi.stubFor(
        post(urlPathMatching("/nbs/redirect/report/management/configuration/add"))
            .willReturn(ok()));

    webClient
        .post()
        .uri(builder -> builder.path("/nbs/NewReport.do").build())
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  void should_route_modernization_404_ui_for_edit() {
    modApi.stubFor(get(urlPathMatching("/nbs/EditReport.do")).willReturn(notFound()));
    classic.stubFor(get(urlPathMatching("/nbs/EditReport.do")).willReturn(ok()));

    webClient
        .get()
        .uri(builder -> builder.path("/nbs/EditReport.do").build())
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  void should_route_modernization_404_ui_for_new_filter() {
    modApi.stubFor(get(urlPathMatching("/nbs/NewReportFilter.do")).willReturn(notFound()));
    classic.stubFor(get(urlPathMatching("/nbs/NewReportFilter.do")).willReturn(ok()));

    webClient
        .get()
        .uri(builder -> builder.path("/nbs/NewReportFilter.do").build())
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  void should_route_modernization_404_ui_for_edit_filter() {
    modApi.stubFor(get(urlPathMatching("/nbs/EditReportFilter.do")).willReturn(notFound()));
    classic.stubFor(get(urlPathMatching("/nbs/EditReportFilter.do")).willReturn(ok()));

    webClient
        .get()
        .uri(builder -> builder.path("/nbs/EditReportFilter.do").build())
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  void should_route_modernization_404_ui_for_edit_filter_with_params() {
    modApi.stubFor(get(urlPathMatching("/nbs/EditReportFilter.do")).willReturn(notFound()));
    classic.stubFor(get(urlPathMatching("/nbs/EditReportFilter.do")).willReturn(ok()));

    webClient
        .get()
        .uri(
            builder ->
                builder.path("/nbs/EditReportFilter.do").queryParam("filter_uid", "1").build())
        .exchange()
        .expectStatus()
        .isNotFound();
  }
}

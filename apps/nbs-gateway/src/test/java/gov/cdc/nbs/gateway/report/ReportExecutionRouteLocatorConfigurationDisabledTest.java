package gov.cdc.nbs.gateway.report;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
      "nbs.gateway.classic=http://localhost:10000",
      "nbs.gateway.modernization.service=localhost:10001",
      "nbs.gateway.report.execution.enabled=false"
    })
class ReportExecutionRouteLocatorConfigurationDisabledTest {

  @RegisterExtension
  static WireMockExtension classic =
      WireMockExtension.newInstance().options(wireMockConfig().port(10000)).build();

  @Autowired WebTestClient webClient;

  @Test
  void should_route_to_classic_when_report_execution_routing_is_disabled() {
    classic.stubFor(
        post(urlPathEqualTo("/nbs/nfc"))
            .willReturn(ok().withBody("{{request.body}}").withTransformers("response-template")));

    MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
    data.add("mode", "edit");
    data.add("ObjectType", "7");
    data.add("OperationType", "117");
    data.add("ReportUID", "2");
    data.add("DataSourceUID", "1");

    webClient
        .post()
        .uri(builder -> builder.path("/nbs/nfc").build())
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters.fromFormData(data))
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(String.class)
        .value(v -> assertThat(v).contains("edit"));
  }
}

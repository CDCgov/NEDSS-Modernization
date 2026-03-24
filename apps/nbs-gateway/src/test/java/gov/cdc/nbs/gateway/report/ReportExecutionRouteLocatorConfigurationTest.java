package gov.cdc.nbs.gateway.report;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;
import wiremock.com.fasterxml.jackson.databind.node.ObjectNode;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
      "nbs.gateway.classic=http://localhost:10000",
      "nbs.gateway.modernization.service=localhost:10001",
      "nbs.gateway.report.execution.enabled=true"
    })
class ReportExecutionRouteLocatorConfigurationTest {

  @RegisterExtension
  static WireMockExtension classic =
      WireMockExtension.newInstance().options(wireMockConfig().port(10000)).build();

  @RegisterExtension
  static WireMockExtension modApi =
      WireMockExtension.newInstance().options(wireMockConfig().port(10001)).build();

  @Autowired WebTestClient webClient;

  @LocalServerPort int gatewayServerPort;

  @Test
  void should_route_modernization_ui_for_run_if_modernized() {

    ObjectMapper mapper = new ObjectMapper();
    ObjectNode node = mapper.createObjectNode();
    node.put("runner", "python");

    modApi.stubFor(
        get(urlPathMatching("/nbs/api/report/configuration/2/1"))
            .willReturn(
                ok().withJsonBody(node)
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

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
        .cookie("test", "123")
        .cookie("test", "456")
        .exchange()
        .expectStatus()
        .isFound()
        .expectHeader()
        .location("http://localhost:%s/report/2/1/run".formatted(gatewayServerPort));
  }

  @Test
  void should_route_classic_ui_for_run_if_not_modernized() {

    ObjectMapper mapper = new ObjectMapper();
    ObjectNode node = mapper.createObjectNode();
    node.put("runner", "sas");

    classic.stubFor(
        post("/nbs/nfc")
            .willReturn(ok().withBody("{{request.body}}").withTransformers("response-template")));

    modApi.stubFor(
        get(urlPathMatching("/nbs/api/report/configuration/2/1"))
            .willReturn(
                ok().withJsonBody(node)
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

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
        .value(v -> assertThat(v).contains("edit"))
        .value(v -> assertThat(v).contains("7"))
        .returnResult();
  }

  @Test
  void should_not_route_to_modernization_when_not_report_object() {

    classic.stubFor(
        post("/nbs/nfc")
            .willReturn(ok().withBody("{{request.body}}").withTransformers("response-template")));

    MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
    data.add("mode", "edit");
    data.add("ObjectType", "8");
    data.add("OperationType", "117");

    webClient
        .post()
        .uri(builder -> builder.path("/nbs/nfc").build())
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters.fromFormData(data))
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(String.class)
        .value(v -> assertThat(v).contains("edit"))
        .value(v -> assertThat(v).contains("8"))
        .returnResult();
  }

  @Test
  void should_not_route_to_modernization_when_not_run() {

    classic.stubFor(
        post("/nbs/nfc")
            .willReturn(ok().withBody("{{request.body}}").withTransformers("response-template")));

    MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
    data.add("mode", "edit");
    data.add("ObjectType", "7");
    data.add("OperationType", "124");

    webClient
        .post()
        .uri(builder -> builder.path("/nbs/nfc").build())
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters.fromFormData(data))
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(String.class)
        .value(v -> assertThat(v).contains("edit"))
        .value(v -> assertThat(v).contains("124"))
        .returnResult();
  }

  @Test
  void should_not_route_to_modernization_when_missing_keys() {

    classic.stubFor(post("/nbs/nfc").willReturn(ok()));

    MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
    data.add("mode", "edit");

    webClient
        .post()
        .uri(builder -> builder.path("/nbs/nfc").build())
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters.fromFormData(data))
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  void should_not_route_to_modernization_when_missing_object_type() {

    classic.stubFor(post("/nbs/nfc").willReturn(ok()));

    MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
    data.add("mode", "edit");
    data.add("OperationType", "124");

    webClient
        .post()
        .uri(builder -> builder.path("/nbs/nfc").build())
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters.fromFormData(data))
        .exchange()
        .expectStatus()
        .isOk();
  }
}

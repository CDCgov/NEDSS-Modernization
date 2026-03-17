package gov.cdc.nbs.gateway.report.execute;

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
      "nbs.gateway.report.execute.enabled=true"
    })
class ReportExecutionLocatorConfigurationTest {

  @RegisterExtension
  static WireMockExtension classic =
      WireMockExtension.newInstance().options(wireMockConfig().port(10000)).build();

  @RegisterExtension
  static WireMockExtension service =
      WireMockExtension.newInstance().options(wireMockConfig().port(10001)).build();

  @Autowired WebTestClient webClient;

  @Test
  void should_route_modernization_api_for_run() {

    service.stubFor(
        post(urlPathMatching("/nbs/redirect/report/execute\\\\?.*"))
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
  void should_route_modernization_api_for_export() {

    service.stubFor(
        post(urlPathMatching("/nbs/redirect/report/execute\\\\?.*"))
            .willReturn(ok().withBody("{{request.body}}").withTransformers("response-template")));

    MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
    data.add("mode", "edit");
    data.add("ObjectType", "7");
    data.add("OperationType", "138");

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
        .value(v -> assertThat(v).contains("138"))
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
        .value(v -> assertThat(v).contains("8"))
        .returnResult();
  }

  @Test
  void should_not_route_to_modernization_when_not_run_or_export() {

    classic.stubFor(
        post("/nbs/nfc")
            .willReturn(ok().withBody("{{request.body}}").withTransformers("response-template")));

    MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
    data.add("mode", "edit");
    data.add("ObjectType", "7");
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
        .value(v -> assertThat(v).contains("117"))
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
}

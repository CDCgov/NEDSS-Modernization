package gov.cdc.nbs.gateway.patient.file.events.report.morbidity;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
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
      "nbs.gateway.patient.file.service=localhost:10001",
      "nbs.gateway.patient.file.enabled=true"
    })
class SubmittedMorbidityAddLocatorConfigurationTest {
  @RegisterExtension
  static WireMockExtension classic =
      WireMockExtension.newInstance().options(wireMockConfig().port(10000)).build();

  @RegisterExtension
  static WireMockExtension service =
      WireMockExtension.newInstance().options(wireMockConfig().port(10001)).build();

  @Autowired WebTestClient webClient;

  @Test
  void should_route_to_service_when_Add_Morbidity_is_submitted() {
    service.stubFor(
        get(urlPathMatching("/nbs/redirect/patient/report/morbidity/submit\\\\?.*"))
            .willReturn(ok()));

    webClient
        .get()
        .uri(builder -> builder.path("/nbs/AddObservationMorb2.do").build())
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  void should_not_route_to_service_when_a_ContextAction_is_present() {
    service.stubFor(
        get(urlPathMatching("/nbs/redirect/patient/report/morbidity/submit\\\\?.*"))
            .willReturn(ok()));

    webClient
        .get()
        .uri(
            builder ->
                builder
                    .path("/nbs/AddObservationMorb2.do")
                    .queryParam("ContextAction", "any")
                    .build())
        .exchange()
        .expectStatus()
        .isNotFound();
  }
}

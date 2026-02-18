package gov.cdc.nbs.gateway.patient.file.events.investigation.delete;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.github.tomakehurst.wiremock.matching.RequestPatternBuilder.allRequests;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
class DeletedInvestigationReturnPatientFileLocatorConfigurationTest {

  @RegisterExtension
  static WireMockExtension classic =
      WireMockExtension.newInstance().options(wireMockConfig().port(10000)).build();

  @RegisterExtension
  static WireMockExtension service =
      WireMockExtension.newInstance().options(wireMockConfig().port(10001)).build();

  @Autowired WebTestClient webClient;

  @ParameterizedTest
  @ValueSource(strings = {"ReturnToFileSummary", "ReturnToFileEvents", "FileSummary"})
  void should_route_to_service_when_Delete_Investigation_is_submitted(final String action) {
    service.stubFor(
        post(urlPathMatching("/nbs/redirect/patient/investigation/delete\\\\?.*"))
            .willReturn(ok()));

    webClient
        .post()
        .uri(
            builder ->
                builder
                    .path("/nbs/PageAction.do")
                    .queryParam("method", "deleteSubmit")
                    .queryParam("ContextAction", action)
                    .build())
        .exchange()
        .expectStatus()
        .isOk();

    classic.verify(0, allRequests());
  }
}

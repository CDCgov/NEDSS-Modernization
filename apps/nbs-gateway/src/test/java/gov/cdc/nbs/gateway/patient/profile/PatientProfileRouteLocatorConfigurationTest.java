package gov.cdc.nbs.gateway.patient.profile;

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
        "nbs.gateway.patient.profile.service=localhost:10001",
        "nbs.gateway.patient.profile.enabled=true"
    }
)
class PatientProfileRouteLocatorConfigurationTest {

  @RegisterExtension
  static WireMockExtension service = WireMockExtension.newInstance()
      .options(wireMockConfig().port(10001))
      .build();

  @Autowired
  WebTestClient webClient;

  @Test
  void should_route_to_service_when_ContextAction_is_ViewFile() {
    service.stubFor(get(urlPathMatching("/nbs/redirect/patientProfile/summary/return\\\\?.*")).willReturn(ok()));

    webClient
        .get().uri(
            builder -> builder
                .path("/nbs/any")
                .queryParam("ContextAction", "ViewFile")
                .build()
        )
        .exchange()
        .expectStatus()
        .isOk();

  }

  @Test
  void should_route_to_service_when_ContextAction_is_FileSummary() {
    service.stubFor(get(urlPathMatching("/nbs/redirect/patientProfile/summary/return\\\\?.*")).willReturn(ok()));

    webClient
        .get().uri(
            builder -> builder
                .path("/nbs/any")
                .queryParam("ContextAction", "FileSummary")
                .build()
        )
        .exchange()
        .expectStatus()
        .isOk();

  }

  @Test
  void should_route_to_patient_profile_from_from_patient_name_in_an_event_search_result() {
    service.stubFor(get(urlPathMatching("/nbs/redirect/patient/profile")).willReturn(ok()));

    webClient
        .get().uri(
            builder -> builder
                .path("/nbs/PatientSearchResults1.do")
                .queryParam("ContextAction", "ViewFile")
                .queryParam("uid", "1051")
                .build()
        )
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  void should_route_to_patient_profile_service_from_patient_name_in_documents_requiring_review_queue() {
    service.stubFor(get(urlPathMatching("/nbs/redirect/patient/profile")).willReturn(ok()));

    webClient
        .get().uri(
            builder -> builder
                .path("/nbs/NewLabReview1.do")
                .queryParam("ContextAction", "ViewFile")
                .queryParam("uid", "1051")
                .build()
        )
        .exchange()
        .expectStatus()
        .isOk();
  }
}

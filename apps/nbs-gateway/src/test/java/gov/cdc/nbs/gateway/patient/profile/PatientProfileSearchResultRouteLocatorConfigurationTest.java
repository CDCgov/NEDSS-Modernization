package gov.cdc.nbs.gateway.patient.profile;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.github.tomakehurst.wiremock.matching.RequestPatternBuilder.allRequests;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "nbs.gateway.classic=http://localhost:10000",
        "nbs.gateway.patient.profile.service=localhost:10001",
        "nbs.gateway.patient.profile.enabled=true"
    }
)
class PatientProfileSearchResultRouteLocatorConfigurationTest {

  @RegisterExtension
  static WireMockExtension classic = WireMockExtension.newInstance()
      .options(wireMockConfig().port(10000))
      .build();

  @RegisterExtension
  static WireMockExtension service = WireMockExtension.newInstance()
      .options(wireMockConfig().port(10001))
      .build();

  @Autowired
  WebTestClient webClient;

  @Test
  void should_route_to_patient_profile_service_when_ContextAction_is_ViewFile_and_uid_is_present() {
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

    classic.verify(0, allRequests());
  }
}

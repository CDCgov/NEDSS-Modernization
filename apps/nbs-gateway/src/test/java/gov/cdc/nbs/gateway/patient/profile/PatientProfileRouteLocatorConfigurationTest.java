package gov.cdc.nbs.gateway.patient.profile;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.stream.Stream;

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

  @RegisterExtension
  static WireMockExtension nbs6 = WireMockExtension.newInstance()
      .options(wireMockConfig().port(10000))
      .build();

  @Autowired
  WebTestClient webClient;

  @ParameterizedTest
  @ValueSource(strings = {"ViewFile", "FileSummary"})
  void should_route_to(final String action) {
    service.stubFor(get(urlPathMatching("/nbs/redirect/patientProfile/summary/return\\\\?.*")).willReturn(ok()));

    webClient
        .get().uri(
            builder -> builder
                .path("/nbs/any/path")
                .queryParam("ContextAction", action)
                .build()
        )
        .exchange()
        .expectStatus()
        .isOk();
  }

  @ParameterizedTest
  @MethodSource("directParameters")
  void should_route_directly_to_the_patient_profile(final String action, final String type) {
    service.stubFor(get(urlPathMatching("/nbs/redirect/patient/profile")).willReturn(ok()));

    webClient
        .get().uri(
            builder -> builder
                .path("/nbs/any/path")
                .queryParam("ContextAction", action)
                .queryParam(type, "1051")
                .build()
        )
        .exchange()
        .expectStatus()
        .isOk();
  }

  static Stream<Arguments> directParameters() {
    return Stream.of(
        Arguments.arguments("ViewFile", "uid"),
        Arguments.arguments("ViewFile", "MPRUid"),
        Arguments.arguments("FileSummary", "uid"),
        Arguments.arguments("FileSummary", "MPRUid")
    );
  }

  @ParameterizedTest
  @ValueSource(strings = {"uid", "MPRUid"})
  void should_only_route_to_the_patient_profile_with_the_known_context_action(final String type) {
    nbs6.stubFor(get(urlPathMatching("/nbs/any/path")).willReturn(ok()));

    webClient
        .get().uri(
            builder -> builder
                .path("/nbs/any/path")
                .queryParam("ContextAction", "Other")
                .queryParam(type, "1051")
                .build()
        )
        .exchange()
        .expectStatus()
        .isOk();
  }
}

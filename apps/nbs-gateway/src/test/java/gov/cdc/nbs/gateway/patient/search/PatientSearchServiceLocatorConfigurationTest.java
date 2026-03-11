package gov.cdc.nbs.gateway.patient.search;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
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
      "nbs.gateway.patient.search.service=localhost:10001",
      "nbs.gateway.patient.search.enabled=true"
    })
class PatientSearchServiceLocatorConfigurationTest {

  @RegisterExtension
  static WireMockExtension classic =
      WireMockExtension.newInstance().options(wireMockConfig().port(10000)).build();

  @RegisterExtension
  static WireMockExtension service =
      WireMockExtension.newInstance().options(wireMockConfig().port(10001)).build();

  @Autowired WebTestClient webClient;

  @Test
  void should_route_modernization_api_for_home_page_advanced_search_link() {

    service.stubFor(get(urlPathMatching("/nbs/redirect/advancedSearch\\\\?.*")).willReturn(ok()));

    webClient
        .get()
        .uri(
            builder ->
                builder
                    .path("/nbs/MyTaskList1.do")
                    .queryParam("ContextAction", "GlobalPatient")
                    .build())
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  void should_not_route_to_modernization_when_context_action_is_not_present() {

    classic.stubFor(get("/nbs/MyTaskList1.do").willReturn(ok()));

    webClient.get().uri("/nbs/MyTaskList1.do").exchange().expectStatus().isOk();
  }

  @Test
  void should_route_modernization_api_for_home_page_search() {

    service.stubFor(post(urlPathMatching("/nbs/redirect/simpleSearch\\\\?.*")).willReturn(ok()));

    webClient
        .post()
        .uri(
            builder ->
                builder
                    .path("/nbs/HomePage.do")
                    .queryParam("method", "patientSearchSubmit")
                    .build())
        .exchange()
        .expectStatus()
        .isOk();
  }
}

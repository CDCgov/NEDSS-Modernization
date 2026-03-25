package gov.cdc.nbs.gateway.system.management;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
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
      "nbs.gateway.system.management.enabled=false",
      "nbs.gateway.classic=http://localhost:10000"
    })
class SystemManagementPageRouteLocatorConfigurationDisabledTest {
  @RegisterExtension
  static WireMockExtension classic =
      WireMockExtension.newInstance().options(wireMockConfig().port(10000)).build();

  @Autowired WebTestClient webClient;

  @Test
  void should_not_route_to_system_management_page_when_disabled() {
    classic.stubFor(get(urlEqualTo("/nbs/SystemAdmin.do")).willReturn(ok()));

    webClient
        .get()
        .uri(builder -> builder.path("/nbs/SystemAdmin.do").build())
        .exchange()
        .expectStatus()
        .isOk();
  }
}

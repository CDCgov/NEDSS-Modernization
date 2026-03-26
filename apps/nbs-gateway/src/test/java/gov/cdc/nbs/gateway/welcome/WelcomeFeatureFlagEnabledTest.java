package gov.cdc.nbs.gateway.welcome;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import gov.cdc.nbs.gateway.GatewayApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
    classes = {GatewayApplication.class, WelcomeServiceProvider.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {"nbs.gateway.ui.service=localhost:10001", "nbs.gateway.welcome.enabled=true"})
@Import(WelcomeServiceProvider.class)
class WelcomeFeatureFlagEnabledTest {

  @RegisterExtension
  static WireMockExtension service =
      WireMockExtension.newInstance().options(wireMockConfig().port(10001)).build();

  @Autowired WebTestClient webClient;

  @Test
  void should_route_to_ui_service() {
    service.stubFor(get(urlEqualTo("/welcome")).willReturn(ok()));

    webClient
        .get()
        .uri(builder -> builder.path("/welcome").build())
        .exchange()
        .expectStatus()
        .isOk();
  }
}

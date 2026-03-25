package gov.cdc.nbs.gateway.landing;

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
    classes = {GatewayApplication.class, LandingServiceProvider.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {"nbs.gateway.landing.base=/", "nbs.gateway.ui.service=localhost:10001"})
@Import(LandingServiceProvider.class)
class LandingRouteLocatorSelfRedirectPreventionTest {

  @RegisterExtension
  static WireMockExtension service =
      WireMockExtension.newInstance().options(wireMockConfig().port(10001)).build();

  @Autowired WebTestClient webClient;

  @Test
  void should_not_redirect_to_itself() {
    service.stubFor(get(urlEqualTo("/")).willReturn(ok()));

    webClient.get().uri(builder -> builder.path("/").build()).exchange().expectStatus().isOk();
  }
}

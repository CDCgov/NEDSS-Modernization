package gov.cdc.nbs.gateway.welcome;

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
    properties = {"nbs.gateway.landing.base=/landing", "nbs.gateway.welcome.enabled=false"})
@Import(WelcomeServiceProvider.class)
class WelcomeFeatureFlagDisabledTest {

  @RegisterExtension
  static WireMockExtension classic =
      WireMockExtension.newInstance().options(wireMockConfig().port(10000)).build();

  @Autowired WebTestClient webClient;

  @Test
  void should_redirect_to_root() {
    webClient
        .get()
        .uri(builder -> builder.path("/welcome").build())
        .exchange()
        .expectHeader()
        .location("/")
        .expectStatus()
        .is3xxRedirection();
  }

  @Test
  void should_redirect_sub_paths_to_root() {
    webClient
        .get()
        .uri(builder -> builder.path("/welcome/learn").build())
        .exchange()
        .expectHeader()
        .location("/")
        .expectStatus()
        .is3xxRedirection();
  }
}

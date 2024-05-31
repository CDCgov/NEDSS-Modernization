package gov.cdc.nbs.gateway.landing;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import gov.cdc.nbs.gateway.GatewayApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(
    classes = {GatewayApplication.class, LandingServiceProvider.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "nbs.gateway.landing.base=/welcome"
    })
@Import(LandingServiceProvider.class)
class LandingServiceTest {

  @RegisterExtension
  static WireMockExtension classic = WireMockExtension.newInstance()
      .options(wireMockConfig().port(10000))
      .build();

  @Autowired
  WebTestClient webClient;

  @Test
  void should_redirect_to_welcome() {
    webClient
        .get().uri(
            builder -> builder
                .path("/")
                .build())
        .exchange()
        .expectHeader().location("/welcome")
        .expectStatus().is3xxRedirection();
  }

}

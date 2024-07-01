package gov.cdc.nbs.security.oidc;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import gov.cdc.nbs.gateway.GatewayApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(
    classes = {GatewayApplication.class, NBS6LogoutRouteLocatorConfiguration.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "nbs.gateway.classic=http://localhost:10000"
    })
@Import(NBS6LogoutRouteLocatorConfiguration.class)
class NBS6LogoutRouteLocatorConfigurationTest {

  @RegisterExtension
  static WireMockExtension classic = WireMockExtension.newInstance()
      .options(wireMockConfig().port(10000))
      .build();

  @Autowired
  WebTestClient webClient;

  @Test
  void should_redirect_to_NBS_logout() {
    classic.stubFor(get(urlPathMatching("/nbs/logout")).willReturn(ok()));

    webClient
        .get().uri(
            builder -> builder
                .path("/nbs/logged-out")
                .build()
        )
        .exchange().expectStatus().isOk();
  }

}

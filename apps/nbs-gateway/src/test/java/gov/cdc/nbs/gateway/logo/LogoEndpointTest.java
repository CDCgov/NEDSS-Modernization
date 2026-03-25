package gov.cdc.nbs.gateway.logo;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.github.tomakehurst.wiremock.matching.RequestPatternBuilder.allRequests;

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
      "nbs.gateway.logo.file=src/test/resources/80x32.png"
    })
class LogoEndpointTest {

  @RegisterExtension
  static WireMockExtension classic =
      WireMockExtension.newInstance().options(wireMockConfig().port(10000)).build();

  @Autowired WebTestClient webClient;

  @Test
  void should_route_to_file_based_logo_() {

    webClient.get().uri("/images/nedssLogo.jpg").exchange().expectStatus().isOk();

    classic.verify(0, allRequests());
  }
}

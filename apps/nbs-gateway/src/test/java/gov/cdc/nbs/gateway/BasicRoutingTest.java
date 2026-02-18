package gov.cdc.nbs.gateway;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {"nbs.gateway.classic=http://localhost:10000"})
class BasicRoutingTest {

  @RegisterExtension
  static WireMockExtension classic =
      WireMockExtension.newInstance().options(wireMockConfig().port(10000)).build();

  @Autowired WebTestClient webClient;

  @Test
  void should_route_nbs_paths_to_classic() {
    classic.stubFor(get("/nbs/HomePage.do").willReturn(ok()));

    webClient.get().uri("/nbs/HomePage.do").exchange().expectStatus().isOk();
  }

  @Test
  void should_route_nbs_paths_to_classic_without_referer() {
    classic.stubFor(get("/nbs/HomePage.do").willReturn(ok()));

    webClient
        .get()
        .uri("/nbs/HomePage.do")
        .header(HttpHeaders.REFERER, "referer-value")
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .doesNotExist(HttpHeaders.REFERER);
  }
}

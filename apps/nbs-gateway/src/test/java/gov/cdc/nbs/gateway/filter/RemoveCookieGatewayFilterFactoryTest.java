package gov.cdc.nbs.gateway.filter;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "test.destination.uri=http://localhost:10000"
    }
)
@DirtiesContext
@ActiveProfiles(profiles = "remove-cookie-gateway-filter")
class RemoveCookieGatewayFilterFactoryTest {

  @RegisterExtension
  static WireMockExtension test = WireMockExtension.newInstance()
      .options(wireMockConfig().port(10000))
      .build();

  @Autowired
  WebTestClient client;

  @Test
  void should_remove_cookie() {
    client.get()
        .uri(
            uri -> uri.path("/remove-cookie-test")
                .build()
        )
        .exchange()
        .expectHeader()
        .value(
            HttpHeaders.SET_COOKIE,
            actual -> assertThat(actual)
                .contains("Secure")
                .containsIgnoringCase("HTTPOnly")
                .contains("cookie-name=;")
                .contains("Max-Age=0;")
                .contains("Path=/path")
        );
  }
}

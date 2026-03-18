package gov.cdc.nbs.gateway.filter;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {"test.destination.uri=http://localhost:10000"})
@DirtiesContext
@ActiveProfiles(profiles = "request-parameter-to-cookie-gateway-filter")
class RequestParameterToCookieGatewayFilterFactoryTest {

  @RegisterExtension
  static WireMockExtension test =
      WireMockExtension.newInstance().options(wireMockConfig().port(10000)).build();

  @Autowired WebTestClient client;

  @Test
  void should_apply_cookie_with_parameter_value() {
    client
        .get()
        .uri(
            uri ->
                uri.path("/request-parameter-to-cookie-test")
                    .queryParam("Parameter", "parameter-value")
                    .build())
        .exchange()
        .expectHeader()
        .value(
            HttpHeaders.SET_COOKIE,
            actual ->
                assertThat(actual)
                    .contains("Secure")
                    .containsIgnoringCase("HTTPOnly")
                    .contains("Cookie=parameter-value"));
  }

  @Test
  void should_not_apply_cookie_when_parameter_value_is_empty() {
    client
        .get()
        .uri(uri -> uri.path("/request-parameter-to-cookie-test").queryParam("Parameter").build())
        .exchange()
        .expectHeader()
        .doesNotExist(HttpHeaders.SET_COOKIE);
  }

  @Test
  void should_not_apply_cookie_when_parameter_not_present() {
    client
        .get()
        .uri(
            uri ->
                uri.path("/request-parameter-to-cookie-test")
                    .queryParam("Other", "other-parameter-value")
                    .build())
        .exchange()
        .expectHeader()
        .doesNotExist(HttpHeaders.SET_COOKIE);
  }
}

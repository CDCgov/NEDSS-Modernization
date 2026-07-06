package gov.cdc.nbs.gateway.report;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.forbidden;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    properties = {
      "nbs.gateway.classic=http://localhost:10000",
      "nbs.gateway.modernization.service=localhost:10001",
      "nbs.gateway.report.execution.enabled=true"
    })
@AutoConfigureWebTestClient
@Import(ReportExecutionOIDCRouteLocatorConfigurationTest.Config.class)
class ReportExecutionOIDCRouteLocatorConfigurationTest {

  static final String TEST_TOKEN = "test-bearer-token";

  /**
   * Builds a pre-canned {@link OAuth2AuthorizedClient} carrying {@link #TEST_TOKEN}. Called both
   * from the {@link Config} bean factory and from {@link #resetMock()} so each test starts with the
   * same default stubbing.
   */
  static OAuth2AuthorizedClient authorizedClient() {
    OAuth2AccessToken token =
        new OAuth2AccessToken(
            OAuth2AccessToken.TokenType.BEARER,
            TEST_TOKEN,
            Instant.now(),
            Instant.now().plusSeconds(3600));

    ClientRegistration registration =
        ClientRegistration.withRegistrationId("test")
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .clientId("test-client")
            .tokenUri("http://localhost/token")
            .build();

    return new OAuth2AuthorizedClient(registration, "test-user", token);
  }

  @TestConfiguration
  static class Config {

    @Bean
    ReactiveOAuth2AuthorizedClientManager mockAuthorizedClientManager() {
      ReactiveOAuth2AuthorizedClientManager manager =
          mock(ReactiveOAuth2AuthorizedClientManager.class);
      when(manager.authorize(any(OAuth2AuthorizeRequest.class)))
          .thenReturn(Mono.just(authorizedClient()));
      return manager;
    }
  }

  @RegisterExtension
  static WireMockExtension classic =
      WireMockExtension.newInstance().options(wireMockConfig().port(10000)).build();

  @RegisterExtension
  static WireMockExtension modApi =
      WireMockExtension.newInstance().options(wireMockConfig().port(10001)).build();

  @Autowired WebTestClient webClient;

  @Autowired ReactiveOAuth2AuthorizedClientManager authorizedClientManager;

  @BeforeEach
  void resetMock() {
    reset(authorizedClientManager);
    when(authorizedClientManager.authorize(any(OAuth2AuthorizeRequest.class)))
        .thenReturn(Mono.just(authorizedClient()));
  }

  @Test
  void should_relay_bearer_token_to_modernization_api_when_oidc_authenticated() {
    // mockOidcLogin() sets an OAuth2AuthenticationToken as the exchange's principal,
    // which resolveBearerToken() picks up to call the mock manager and get TEST_TOKEN.
    modApi.stubFor(
        get(urlPathMatching("/nbs/api/report/runner/2/1"))
            .willReturn(
                ok().withBody("python")
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

    MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
    data.add("ObjectType", "7");
    data.add("OperationType", "117");
    data.add("ReportUID", "2");
    data.add("DataSourceUID", "1");

    webClient
        .mutateWith(SecurityMockServerConfigurers.mockOidcLogin())
        .post()
        .uri("/nbs/nfc")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters.fromFormData(data))
        .exchange()
        .expectStatus()
        .isFound();

    modApi.verify(
        getRequestedFor(urlPathMatching("/nbs/api/report/runner/2/1"))
            .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + TEST_TOKEN)));
  }

  @Test
  void should_not_relay_bearer_token_when_principal_is_not_oauth2() {
    // Without mockOidcLogin(), exchange.getPrincipal() is empty — resolveBearerToken()
    // short-circuits before calling the manager and returns Mono.empty().
    modApi.stubFor(
        get(urlPathMatching("/nbs/api/report/runner/2/1"))
            .willReturn(
                ok().withBody("python")
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

    MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
    data.add("ObjectType", "7");
    data.add("OperationType", "117");
    data.add("ReportUID", "2");
    data.add("DataSourceUID", "1");

    webClient
        .post()
        .uri("/nbs/nfc")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters.fromFormData(data))
        .exchange()
        .expectStatus()
        .isFound();

    modApi.verify(
        getRequestedFor(urlPathMatching("/nbs/api/report/runner/2/1"))
            .withoutHeader(HttpHeaders.AUTHORIZATION));
  }

  @Test
  void should_fall_through_to_classic_when_token_resolution_fails() {
    // When the manager throws, resolveBearerToken()'s onErrorResume catches it and returns
    // Mono.empty(), so no bearer token is attached. The mod-api call then gets a 403
    // (no valid auth), and the predicate falls through to the classic route.
    when(authorizedClientManager.authorize(any(OAuth2AuthorizeRequest.class)))
        .thenReturn(Mono.error(new RuntimeException("token resolution failed")));

    classic.stubFor(post("/nbs/nfc").willReturn(ok()));
    modApi.stubFor(get(urlPathMatching("/nbs/api/report/runner/2/1")).willReturn(forbidden()));

    MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
    data.add("ObjectType", "7");
    data.add("OperationType", "117");
    data.add("ReportUID", "2");
    data.add("DataSourceUID", "1");

    webClient
        .mutateWith(SecurityMockServerConfigurers.mockOidcLogin())
        .post()
        .uri("/nbs/nfc")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters.fromFormData(data))
        .exchange()
        .expectStatus()
        .isOk();
  }
}

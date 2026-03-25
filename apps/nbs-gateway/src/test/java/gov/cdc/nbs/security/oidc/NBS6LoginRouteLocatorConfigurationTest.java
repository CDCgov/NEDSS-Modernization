package gov.cdc.nbs.security.oidc;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import gov.cdc.nbs.gateway.GatewayApplication;
import gov.cdc.nbs.gateway.home.HomeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
    classes = {GatewayApplication.class, NBS6LoginRouteLocatorConfiguration.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {"nbs.gateway.classic=http://localhost:10000"})
class NBS6LoginRouteLocatorConfigurationTest {

  @RegisterExtension
  static WireMockExtension classic =
      WireMockExtension.newInstance().options(wireMockConfig().port(10000)).build();

  @Autowired ApplicationContext context;

  @Autowired WebTestClient webClient;

  @Autowired HomeService home;

  @Test
  void should_bypass_NBS6_login() {
    classic.stubFor(get(urlEqualTo("/nbs/nfc?UserName=authenticated-user")));

    WebTestClient.bindToApplicationContext(this.context)
        // add Spring Security test Support
        .apply(springSecurity())
        .configureClient()
        .build()
        .mutateWith(SecurityMockServerConfigurers.mockUser("authenticated-user"))
        .get()
        .uri(builder -> builder.path("/nbs/login").build())
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  void should_block_NBS6_nfc_based_login() {
    webClient
        .get()
        .uri(builder -> builder.path("/nbs/nfc").queryParam("UserName", "anything").build())
        .exchange()
        .expectStatus()
        .is3xxRedirection()
        .expectHeader()
        .location(home.base());
  }

  @Test
  void should_allow_other_NBS6_nfc_traffic() {
    classic.stubFor(get(urlPathEqualTo("/nbs/nfc")).willReturn(ok()));

    webClient
        .get()
        .uri(builder -> builder.path("/nbs/nfc").build())
        .exchange()
        .expectStatus()
        .isOk();
  }
}

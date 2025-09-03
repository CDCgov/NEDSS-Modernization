package gov.cdc.nbs.gateway.classic.report;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "nbs.gateway.classic=http://localhost:10000"
    }
)
class NBS6ReportCancelRouteLocatorConfigurationTest {

  @RegisterExtension
  static WireMockExtension classic = WireMockExtension.newInstance()
      .options(wireMockConfig().port(10000))
      .build();

  @Autowired
  WebTestClient webClient;

  @Test
  void should_should_clear_NBS_Report_cookie_if_present() {
    classic.stubFor(get(urlEqualTo("/nbs/ManageReports.do")).willReturn(ok()));

    webClient
        .get().uri(
            builder -> builder
                .path("/nbs/ManageReports.do")
                .build()
        ).cookie("NBS-Report", "basic")
        .exchange()
        .expectHeader()
        .value(
            HttpHeaders.SET_COOKIE,
            allOf(
                containsString("NBS-Report=;"),
                containsString("Path=/nbs"),
                containsString("Secure"),
                containsString("HttpOnly"),
                containsString("SameSite=Strict"),
                containsString("Max-Age=0"),
                containsString("Expires=Thu, 01 Jan 1970 00:00:00 GMT")
            )
        )
        .expectStatus()
        .isOk();
  }

  @Test
  void should_should_not_clear_NBS_Report_cookie_when_not_present() {
    classic.stubFor(get(urlEqualTo("/nbs/ManageReports.do")).willReturn(ok()));

    webClient
        .get().uri(
            builder -> builder
                .path("/nbs/ManageReports.do")
                .build()
        )
        .exchange()
        .expectHeader()
        .doesNotExist(HttpHeaders.SET_COOKIE)
        .expectStatus()
        .isOk();
  }
}

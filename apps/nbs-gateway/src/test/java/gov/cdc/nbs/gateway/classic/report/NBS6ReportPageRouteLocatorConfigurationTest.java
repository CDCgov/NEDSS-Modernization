package gov.cdc.nbs.gateway.classic.report;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import gov.cdc.nbs.gateway.classic.NBSClassicService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "nbs.gateway.classic=http://localhost:10000"
    }
)
class NBS6ReportPageRouteLocatorConfigurationTest {

  @RegisterExtension
  static WireMockExtension classic = WireMockExtension.newInstance()
      .options(wireMockConfig().port(10000))
      .build();

  @Autowired
  WebTestClient webClient;

  @Autowired
  NBSClassicService service;

  @ParameterizedTest
  @ValueSource(strings = {"reports", "basic", "advanced", "column", "run", "save", "error"})
  void should_apply_NBS_report_cookies_with_report_page(final String page) {
    classic.stubFor(get(urlEqualTo("/nbs/report/" + page)).willReturn(ok()));

    webClient
        .get().uri(
            builder -> builder
                .path("/nbs/report/" + page)
                .build()
        )
        .exchange()
        .expectCookie()
        .value("NBS-Report", equalTo(page))
        .expectCookie()
        .path("NBS-Report", "/nbs")
        .expectCookie()
        .httpOnly("NBS-Report", true)
        .expectCookie()
        .secure("NBS-Report", true)
        .expectCookie()
        .sameSite("NBS-Report", "Strict")
    ;
  }

  @Test
  void should_not_apply_NBS_report_cookies_proxy_route() {
    classic.stubFor(get(urlEqualTo("/nbs/report/proxy")).willReturn(ok()));

    webClient
        .get().uri(
            builder -> builder
                .path("/nbs/report/proxy")
                .build()
        )
        .exchange()
        .expectCookie()
        .doesNotExist("NBS-Report");
  }

  @Test
  void should_not_apply_NBS_report_cookies_for_report_assets() {
    classic.stubFor(get(urlEqualTo("/nbs/report/asset.gif")).willReturn(ok()));

    webClient
        .get().uri(
            builder -> builder
                .path("/nbs/report/asset.gif")
                .build()
        )
        .exchange()
        .expectCookie()
        .doesNotExist("NBS-Report");
  }

  @Test
  void should_not_apply_NBS_report_cookies_for_sub_path_report_assets() {
    classic.stubFor(get(urlEqualTo("/nbs/report/basic/asset.gif")).willReturn(ok()));

    webClient
        .get().uri(
            builder -> builder
                .path("/nbs/report/basic/asset.gif")
                .build()
        )
        .exchange()
        .expectCookie()
        .doesNotExist("NBS-Report");
  }
}

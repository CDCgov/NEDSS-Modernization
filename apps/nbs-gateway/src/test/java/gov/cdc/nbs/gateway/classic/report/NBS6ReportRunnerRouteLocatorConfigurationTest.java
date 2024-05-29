package gov.cdc.nbs.gateway.classic.report;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import gov.cdc.nbs.gateway.classic.NBSClassicService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "nbs.gateway.classic=http://localhost:10000"
    }
)
class NBS6ReportRunnerRouteLocatorConfigurationTest {

  @RegisterExtension
  static WireMockExtension classic = WireMockExtension.newInstance()
      .options(wireMockConfig().port(10000))
      .build();

  @Autowired
  WebTestClient webClient;

  @Autowired
  NBSClassicService service;

  @Test
  void should_apply_report_path_to_referer_from_the_NBS_Report_cookie() {
    classic.stubFor(
        post(urlEqualTo("/nbs/nfc"))
            .withHeader(HttpHeaders.REFERER, equalTo("http://localhost:10000/report/basic"))
            .willReturn(ok())
    );

    webClient
        .post().uri(
            builder -> builder
                .path("/nbs/nfc")
                .build()
        ).cookie("NBS-Report", "basic")
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  void should_not_apply_report_path_to_referer_without_the_NBS_Report_cookie() {
    classic.stubFor(
        post(urlEqualTo("/nbs/nfc"))
            .withHeader(HttpHeaders.REFERER, equalTo("http://localhost:10000/report/basic"))
            .willReturn(badRequest())
    );

    classic.stubFor(
        post(urlEqualTo("/nbs/nfc"))
            .willReturn(ok())
    );

    webClient
        .post().uri(
            builder -> builder
                .path("/nbs/nfc")
                .build()
        )
        .exchange()
        .expectStatus()
        .isOk();
  }
}

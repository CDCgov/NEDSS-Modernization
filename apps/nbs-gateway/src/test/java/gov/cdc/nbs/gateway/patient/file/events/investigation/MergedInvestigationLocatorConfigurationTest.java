package gov.cdc.nbs.gateway.patient.file.events.investigation;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "nbs.gateway.classic=http://localhost:10000",
        "nbs.gateway.patient.file.service=localhost:10001",
        "nbs.gateway.patient.file.enabled=true"
    }
)
class MergedInvestigationLocatorConfigurationTest {

    @RegisterExtension
    static WireMockExtension classic = WireMockExtension.newInstance()
        .options(wireMockConfig().port(10000))
        .build();

    @RegisterExtension
    static WireMockExtension service = WireMockExtension.newInstance()
        .options(wireMockConfig().port(10001))
        .build();

    @Autowired
    WebTestClient webClient;

    @Test
    void should_route_to_service_when_Merge_Investigation_is_completed() {
        service.stubFor(post(urlPathMatching("/nbs/redirect/patient/investigation/merge\\\\?.*")).willReturn(ok()));

        webClient
            .post().uri(
                builder -> builder
                    .path("/nbs/PageAction.do")
                    .queryParam("ContextAction", "Submit")
                    .queryParam("method", "mergeSubmit")
                    .build()
            )
            .exchange()
            .expectStatus()
            .isOk();
    }
}

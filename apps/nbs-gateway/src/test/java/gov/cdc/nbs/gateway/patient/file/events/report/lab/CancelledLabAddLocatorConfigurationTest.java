package gov.cdc.nbs.gateway.patient.file.events.report.lab;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "nbs.gateway.classic=http://localhost:10000",
        "nbs.gateway.patient.file.service=localhost:10001",
        "nbs.gateway.patient.file.enabled=true"
})
class CancelledLabAddLocatorConfigurationTest {

    @RegisterExtension
    static WireMockExtension service = WireMockExtension.newInstance()
            .options(wireMockConfig().port(10001))
            .build();

    @Autowired
    WebTestClient webClient;

    @Test
    void should_route_to_service_when_Add_Lab_is_cancelled() {
        service.stubFor(get(urlPathMatching("/nbs/redirect/patient/file/events/return\\\\?.*")).willReturn(ok()));

        webClient
                .get().uri(
                        builder -> builder
                                .path("/nbs/AddObservationLab2.do")
                                .queryParam("ContextAction", "Cancel")
                                .build())
                .exchange()
                .expectStatus()
                .isOk();
    }

}

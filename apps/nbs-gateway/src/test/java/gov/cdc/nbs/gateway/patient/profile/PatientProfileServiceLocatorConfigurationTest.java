package gov.cdc.nbs.gateway.patient.profile;

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

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "nbs.gateway.classic=http://localhost:10000",
        "nbs.gateway.patient.profile.service=localhost:10001",
        "nbs.gateway.patient.profile.enabled=true"
    }
)
class PatientProfileServiceLocatorConfigurationTest {

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
    void should_route_to_service_when_ContextAction_is_ViewFile() {
        service.stubFor(get(urlPathMatching("/nbs/redirect/patientProfile\\\\?.*")).willReturn(ok()));

        webClient
            .get().uri(
                builder -> builder
                    .path("/nbs/any")
                    .queryParam("ContextAction", "ViewFile")
                    .build()
            )
            .exchange()
            .expectStatus()
            .isOk();
    }

    @Test
    void should_route_to_service_when_ContextAction_is_FileSummary() {
        service.stubFor(get(urlPathMatching("/nbs/redirect/patientProfile\\\\?.*")).willReturn(ok()));

        webClient
            .get().uri(
                builder -> builder
                    .path("/nbs/any")
                    .queryParam("ContextAction", "FileSummary")
                    .build()
            )
            .exchange()
            .expectStatus()
            .isOk();
    }
}

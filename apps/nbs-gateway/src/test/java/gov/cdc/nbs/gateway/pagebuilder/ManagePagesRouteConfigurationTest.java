package gov.cdc.nbs.gateway.pagebuilder;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "nbs.gateway.pagebuilder.manage.service=localhost:10002",
                "nbs.gateway.pagebuilder.manage.enabled=true"
        })
class ManagePagesRouteConfigurationTest {

    @RegisterExtension
    static WireMockExtension service = WireMockExtension.newInstance()
            .options(wireMockConfig().port(10002))
            .build();

    @Autowired
    WebTestClient webClient;


    @Test
    void should_route() {

        service.stubFor(get(urlPathMatching("/nbs/redirect/pagebuilder/manage/pages")).willReturn(ok()));

        webClient
                .get().uri(
                        builder -> builder
                                .path("/nbs/ManagePage.do")
                                .queryParam("method", "list")
                                .queryParam("initLoad", "true")
                                .build())
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void should_not_route_without_init_load_param() {
        webClient
                .get().uri(
                        builder -> builder
                                .path("/nbs/ManagePage.do")
                                .queryParam("method", "list")
                                .build())
                .exchange()
                .expectStatus()
                .isFound();
    }

    @Test
    void should_not_route_without_method_param() {
        webClient
                .get().uri(
                        builder -> builder
                                .path("/nbs/ManagePage.do")
                                .queryParam("initLoad", "true")
                                .build())
                .exchange()
                .expectStatus()
                .isFound();
    }
}

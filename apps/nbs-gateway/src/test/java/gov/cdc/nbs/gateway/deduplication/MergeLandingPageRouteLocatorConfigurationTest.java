package gov.cdc.nbs.gateway.deduplication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
    "nbs.gateway.deduplication.merge.enabled=true"
})
class MergeLandingPageRouteLocatorConfigurationTest {

  @Autowired
  WebTestClient webClient;

  @Test
  void should_route_to_deduplication_merge_landing_page() {
    webClient
        .get().uri(
            builder -> builder
                .path("/nbs/LoadMergeCandidateList2.do")
                .queryParam("ContextAction", "GlobalMP_SystemIndentified")
                .build())
        .exchange()
        .expectStatus()
        .isFound()
        .expectHeader().location("/deduplication/merge");
  }
}

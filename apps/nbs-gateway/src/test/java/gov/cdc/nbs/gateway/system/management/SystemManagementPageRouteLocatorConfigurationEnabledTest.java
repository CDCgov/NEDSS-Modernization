package gov.cdc.nbs.gateway.system.management;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
      "nbs.gateway.system.management.enabled=true",
      "nbs.gateway.classic=http://localhost:10000"
    })
class SystemManagementPageRouteLocatorConfigurationEnabledTest {
  @Autowired WebTestClient webClient;

  @Test
  void should_route_to_system_management() {
    webClient
        .get()
        .uri(builder -> builder.path("/nbs/SystemAdmin.do").build())
        .exchange()
        .expectStatus()
        .isFound()
        .expectHeader()
        .location("/system/management");
  }
}

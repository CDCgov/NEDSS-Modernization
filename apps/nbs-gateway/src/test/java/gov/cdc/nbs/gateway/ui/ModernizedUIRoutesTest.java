package gov.cdc.nbs.gateway.ui;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ModernizedUIRoutesTest {

  @Autowired
  WebTestClient webClient;

  @Test
  void should_redirect_the_timeout_page_to_the_ui_expired_page() {
    webClient
        .get().uri(
            builder -> builder
                .path("/nbs/timeout")
                .build()
        )
        .exchange()
        .expectStatus()
        .is3xxRedirection().expectHeader().location("/expired");
  }

  @Test
  void should_route_the_logOut_page_to_the_ui_goodbye_page() {
    webClient
        .get().uri(
            builder -> builder
                .path("/nbs/logOut")
                .build()
        )
        .exchange()
        .expectStatus()
        .is3xxRedirection().expectHeader().location("/goodbye");
  }
}

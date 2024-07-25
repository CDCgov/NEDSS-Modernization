package gov.cdc.nbs.patient.search.redirect;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
    properties = {
        "nbs.ui.features.search.view.enabled=true"
    },
    classes = SearchRedirectConfiguration.class
)
class SearchRedirectConfigurationTest {

  @Autowired
  SearchRedirect searchRedirect;

  @Test
  void should_resolve_to_search_path() {

    assertThat(searchRedirect.base()).isEqualTo("/search");

  }
}

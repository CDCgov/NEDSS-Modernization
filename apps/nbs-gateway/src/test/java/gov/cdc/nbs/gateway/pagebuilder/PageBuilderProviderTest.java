package gov.cdc.nbs.gateway.pagebuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;

class PageBuilderProviderTest {

  @Test
  void should_return_valid_uri() throws URISyntaxException {
    PageBuilderProvider provider = new PageBuilderProvider();
    PageBuilderService service =
        provider.pageBuilderManagePagesRoute("http", "google.com", "base/path");

    assertThat(service.base()).isEqualTo("base/path");

    assertEquals("http://google.com", service.uri().toString());
  }
}

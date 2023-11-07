package gov.cdc.nbs.gateway.pagebuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;

class PageBuilderProviderTest {

  @Test
  void should_return_valid_uri() throws URISyntaxException {
    PageBuilderProvider provider = new PageBuilderProvider();
    PageBuilderService service = provider.pagebuilderManagePagesRoute("http", "google.com");
    assertEquals("http://google.com", service.uri().toString());
  }
}

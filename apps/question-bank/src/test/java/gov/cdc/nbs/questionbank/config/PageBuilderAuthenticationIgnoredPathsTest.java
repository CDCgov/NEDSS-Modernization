package gov.cdc.nbs.questionbank.config;

import static org.junit.Assert.assertNotNull;
import java.util.Collection;
import org.junit.jupiter.api.Test;

class PageBuilderAuthenticationIgnoredPathsTest {

  PageBuilderAuthenticationIgnoredPaths ignoredPaths = new PageBuilderAuthenticationIgnoredPaths();

  @Test
  void should_list_paths() {
    Collection<String> paths = ignoredPaths.get();
    assertNotNull(paths);
  }

  @Test
  void should_return_array() {
    String[] paths = ignoredPaths.asArray();
    assertNotNull(paths);
  }
}

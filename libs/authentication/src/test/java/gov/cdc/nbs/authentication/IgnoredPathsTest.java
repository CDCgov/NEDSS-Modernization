package gov.cdc.nbs.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class IgnoredPathsTest {

  @Test
  void should_ignore_all_request() {
    // Given an ignore all paths
    IgnoredPaths ignoredPaths = new IgnoredPaths("/**");

    // and a request
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getServletPath()).thenReturn("/ignored/path");

    // When the request is checked to be ignored
    boolean ignore = ignoredPaths.ignored(request);

    // Then the request is ignored
    assertTrue(ignore);
  }

  @Test
  void should_ignore_request() {
    // Given an ignore path
    IgnoredPaths ignoredPaths = new IgnoredPaths("/some/path");

    // and a request with matching path
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getServletPath()).thenReturn("/some/path");

    // When the request is checked to be ignored
    boolean ignore = ignoredPaths.ignored(request);

    // Then the request is ignored
    assertTrue(ignore);
  }

  @Test
  void should_not_ignore_request() {
    // Given an ignore path
    IgnoredPaths ignoredPaths = new IgnoredPaths("/some/path");

    // and a request
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getServletPath()).thenReturn("/some/other/path");

    // When the request is checked to be ignore
    boolean ignore = ignoredPaths.ignored(request);

    // Then the request is not ignored
    assertFalse(ignore);
  }

  @Test
  void should_return_paths() {
    // Given an ignore path
    IgnoredPaths ignoredPaths = new IgnoredPaths("/some/path", "/some/other/path");

    // When the paths are extracted
    String[] paths = ignoredPaths.paths();

    // Then the expected paths should be returned
    assertEquals("/some/path", paths[0]);
    assertEquals("/some/other/path", paths[1]);
  }

}

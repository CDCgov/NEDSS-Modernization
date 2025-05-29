package gov.cdc.nbs.authentication;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

class IgnoredPathsTest {

  @Test
  void should_ignore_all_request() {
    // Given an ignore all paths
    IgnoredPaths ignoredPaths = new IgnoredPaths("/**");

    // and a request
    HttpServletRequest request = new MockHttpServletRequest("GET", "/ignored/path");

    // When the request is checked to be ignored
    boolean actual = ignoredPaths.ignored(request);

    // Then the request is ignored
    assertThat(actual).isTrue();
  }

  @Test
  void should_ignore_request() {
    // Given an ignore path
    IgnoredPaths ignoredPaths = new IgnoredPaths("/some/path");

    // and a request with matching path
    HttpServletRequest request = new MockHttpServletRequest("GET", "/some/path");

    // When the request is checked to be ignored
    boolean actual = ignoredPaths.ignored(request);

    // Then the request is ignored
    assertThat(actual).isTrue();
  }

  @Test
  void should_not_ignore_request() {
    // Given an ignore path
    IgnoredPaths ignoredPaths = new IgnoredPaths("/some/path");

    // and a request
    HttpServletRequest request = new MockHttpServletRequest("GET", "/some/other/path");

    // When the request is checked to be ignored
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
    assertThat(paths).contains("/some/path", "/some/other/path");
  }

}

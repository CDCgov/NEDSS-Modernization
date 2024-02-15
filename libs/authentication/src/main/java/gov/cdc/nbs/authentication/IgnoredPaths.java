package gov.cdc.nbs.authentication;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class IgnoredPaths {

  private final String[] paths;
  private final Collection<AntPathRequestMatcher> matchers;

  public IgnoredPaths(final String... paths) {
    this.paths = paths;
    this.matchers = Arrays.stream(paths)
        .map(AntPathRequestMatcher::new)
        .toList();
  }

  public IgnoredPaths(final List<String> paths) {
    this(paths.toArray(String[]::new));
  }

  public boolean ignored(final HttpServletRequest request) {
    return this.matchers.stream().anyMatch(matcher -> matcher.matches(request));
  }

  public String[] paths() {
    return paths;
  }

}

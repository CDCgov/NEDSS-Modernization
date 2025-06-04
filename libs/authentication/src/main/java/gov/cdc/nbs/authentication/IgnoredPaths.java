package gov.cdc.nbs.authentication;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IgnoredPaths {

  private final String[] paths;

  private final RequestMatcher matcher;

  public IgnoredPaths(final String... paths) {
    this.paths = paths;
    this.matcher = paths.length == 0
        ? new NegatedRequestMatcher(AnyRequestMatcher.INSTANCE)
        : resolveMatcher(paths);
  }

  private RequestMatcher resolveMatcher(final String... paths) {
    PathPatternRequestMatcher.Builder builder = PathPatternRequestMatcher.withDefaults();
    List<RequestMatcher> list = Arrays.stream(paths)
        .map(builder::matcher)
        .collect(Collectors.toList());

    return new OrRequestMatcher(list);
  }

  public IgnoredPaths(final List<String> paths) {
    this(paths.toArray(String[]::new));
  }

  public boolean ignored(final HttpServletRequest request) {
    return this.matcher.matches(request);
  }

  public String[] paths() {
    return paths;
  }

}

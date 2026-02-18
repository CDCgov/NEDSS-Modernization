package gov.cdc.nbs.authentication;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

class NBSAuthenticationConfigurer implements AuthenticationConfigurer {

  private final IgnoredPaths ignoredPaths;
  private final NBSAuthenticationFilterFactory nbsAuthenticationFilterFactory;

  NBSAuthenticationConfigurer(
      final IgnoredPaths ignoredPaths,
      final NBSAuthenticationFilterFactory nbsAuthenticationFilterFactory) {
    this.ignoredPaths = ignoredPaths;
    this.nbsAuthenticationFilterFactory = nbsAuthenticationFilterFactory;
  }

  public HttpSecurity configure(final HttpSecurity http) {
    return http.addFilterBefore(
        nbsAuthenticationFilterFactory.ignoring(ignoredPaths),
        UsernamePasswordAuthenticationFilter.class);
  }
}

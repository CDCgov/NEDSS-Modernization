package gov.cdc.nbs.authentication;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfigurer {

  private final IgnoredPaths ignoredPaths;
  private final AuthenticationConfigurer authenticationConfigurer;

  public SecurityConfigurer(
      final IgnoredPaths ignoredPaths,
      final AuthenticationConfigurer authenticationConfigurer
  ) {
    this.ignoredPaths = ignoredPaths;
    this.authenticationConfigurer = authenticationConfigurer;
  }

  public HttpSecurity configure(final HttpSecurity http) throws Exception {
    return authenticationConfigurer.configure(withStandardSecurity(http));
  }

  private HttpSecurity withStandardSecurity(final HttpSecurity http) throws Exception {
    return http
        .csrf().disable()
        .authorizeHttpRequests(requests -> requests.requestMatchers(ignoredPaths.paths()).permitAll())
        .authorizeHttpRequests(requests -> requests.anyRequest().authenticated())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(
            exceptions -> exceptions.defaultAuthenticationEntryPointFor(
                new NBSSessionAuthenticationEntryPoint(),
                AnyRequestMatcher.INSTANCE
            ).defaultAccessDeniedHandlerFor(
                new NBSAccessDeniedHandler(),
                AnyRequestMatcher.INSTANCE
            )
        );
  }
}

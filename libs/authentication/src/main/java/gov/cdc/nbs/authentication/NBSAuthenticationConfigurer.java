package gov.cdc.nbs.authentication;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class NBSAuthenticationConfigurer {

  private final IgnoredPaths ignoredPaths;
  private final NBSAuthenticationFilterFactory nbsAuthenticationFilterFactory;

  public NBSAuthenticationConfigurer(
      final IgnoredPaths ignoredPaths,
      final NBSAuthenticationFilterFactory nbsAuthenticationFilterFactory
  ) {
    this.ignoredPaths = ignoredPaths;
    this.nbsAuthenticationFilterFactory = nbsAuthenticationFilterFactory;
  }

  public HttpSecurity configure(final HttpSecurity http) throws Exception {
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
        )
        .addFilterBefore(
            nbsAuthenticationFilterFactory.ignoring(ignoredPaths),
            UsernamePasswordAuthenticationFilter.class
        );
  }
}

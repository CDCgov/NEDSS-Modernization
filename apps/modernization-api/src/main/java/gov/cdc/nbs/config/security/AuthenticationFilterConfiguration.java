package gov.cdc.nbs.config.security;


import gov.cdc.nbs.authentication.NBSAuthenticationFilter;
import gov.cdc.nbs.authentication.UserService;
import gov.cdc.nbs.authentication.config.SecurityProperties;
import gov.cdc.nbs.authentication.session.AuthorizedSessionResolver;
import gov.cdc.nbs.authentication.token.NBSTokenCookieEnsurer;
import gov.cdc.nbs.authentication.token.NBSTokenValidator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AuthenticationFilterConfiguration {

  /**
   * Creates and configures a {@code NBSAuthenticationFilter} that will be applied to all incoming requests.
   */
  @Bean
  FilterRegistrationBean<NBSAuthenticationFilter> authenticationFilterRegistration(
      final NBSTokenValidator tokenValidator,
      final AuthorizedSessionResolver sessionResolver,
      final NBSTokenCookieEnsurer cookieEnsurer,
      final SecurityProperties securityProperties,
      final UserService userService) {

    NBSAuthenticationFilter filter = new NBSAuthenticationFilter(
        tokenValidator,
        sessionResolver,
        cookieEnsurer,
        securityProperties,
        userService);

    FilterRegistrationBean<NBSAuthenticationFilter> registration = new FilterRegistrationBean<>(filter);
    registration.addUrlPatterns("/*");
    return registration;
  }
}

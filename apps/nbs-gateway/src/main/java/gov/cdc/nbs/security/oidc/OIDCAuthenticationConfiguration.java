package gov.cdc.nbs.security.oidc;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@Configuration
class OIDCAuthenticationConfiguration {

  @Bean
  SecurityWebFilterChain oidcAuthentication(
      final ServerHttpSecurity http, final ReactiveClientRegistrationRepository repository) {
    return http.authorizeExchange(
            authorize ->
                //  the landing page
                authorize
                    .pathMatchers(HttpMethod.GET, "/welcome/**")
                    .permitAll()
                    //  paths associated with authentication
                    .pathMatchers(HttpMethod.GET, "/nbs/logged-out", "/nbs/timeout", "/nbs/logOut")
                    .permitAll()
                    //  assets that do not require authentication
                    .pathMatchers(
                        HttpMethod.GET,
                        "/",
                        "/nbs/*.js",
                        "/nbs/*.css",
                        "/nbs/*.gif",
                        "/nbs/task_button/**",
                        "/images/nedssLogo.jpg",
                        "/favicon.ico",
                        "/static/**",
                        "/logout",
                        "/goodbye",
                        "/expired")
                    .permitAll()
                    // monitoring endpoints that do not require authentication
                    .pathMatchers(HttpMethod.GET, "/~/**")
                    .permitAll()
                    .anyExchange()
                    .authenticated())
        .oauth2Client(withDefaults())
        .oauth2Login(withDefaults())
        .logout(
            logout ->
                logout
                    .requiresLogout(ServerWebExchangeMatchers.pathMatchers("/nbs/logout"))
                    .logoutSuccessHandler(oidcLogoutSuccessHandler(repository)))
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
        .build();
  }

  private ServerLogoutSuccessHandler oidcLogoutSuccessHandler(
      final ReactiveClientRegistrationRepository repository) {
    OidcClientInitiatedServerLogoutSuccessHandler oidcLogoutSuccessHandler =
        new OidcClientInitiatedServerLogoutSuccessHandler(repository);

    // Sets the location that the End-User's User Agent will be redirected to
    // after the logout has been performed at the Provider
    oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/nbs/logged-out");

    return oidcLogoutSuccessHandler;
  }
}

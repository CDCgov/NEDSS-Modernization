package gov.cdc.nbs.gateway.filter;

import java.util.List;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * A {@link GatewayFilter} factory that will remove a cookie by adding a {@code Set-Cookie} header
 * to the response with a blank value and max-age of zero.
 *
 * <pre>
 * spring:
 *   cloud:
 *     gateway:
 *       routes:
 *         - id: remove-cookie
 *           uri: https://example.org
 *           predicates:
 *             - Path=/
 *           filters:
 *             - RemoveCookie=Path, CookieName
 * </pre>
 *
 * <p>This adds a {@code Set-Cookie} header with a value of {@code Cookie=parameter-value; Secure;
 * HttpOnly}
 */
@Component
public class RemoveCookieGatewayFilterFactory
    extends AbstractGatewayFilterFactory<RemoveCookieGatewayFilterFactory.Config> {

  public record Config(String path, String cookie) {}

  public RemoveCookieGatewayFilterFactory() {
    super(Config.class);
  }

  @Override
  public List<String> shortcutFieldOrder() {
    return List.of("path", "cookie");
  }

  @Override
  public GatewayFilter apply(final Config config) {
    return ((exchange, chain) ->
        chain.filter(exchange).then(Mono.fromRunnable(() -> execute(config, exchange))));
  }

  private void execute(final Config config, final ServerWebExchange exchange) {
    exchange.getResponse().addCookie(create(config.path(), config.cookie()));
  }

  private ResponseCookie create(final String path, final String name) {
    return ResponseCookie.from(name)
        .path(path)
        .sameSite("Strict")
        .httpOnly(true)
        .secure(true)
        .maxAge(0)
        .build();
  }
}

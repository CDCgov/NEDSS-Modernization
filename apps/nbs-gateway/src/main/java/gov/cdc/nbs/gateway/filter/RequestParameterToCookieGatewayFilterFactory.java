package gov.cdc.nbs.gateway.filter;

import java.util.List;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * A {@link GatewayFilter} factory that will add a {@code Set-Cookie} header to the response with
 * the value of a request parameter from the request. The cookie will only be added if the request
 * parameter exists and the value exists.
 *
 * <pre>
 * spring:
 *   cloud:
 *     gateway:
 *       routes:
 *         - id: request-parameter-to-cookie
 *           uri: https://example.org
 *           predicates:
 *             - Path=/
 *           filters:
 *             - RequestParameterToCookie=ParameterName, CookieName
 * </pre>
 *
 * <p>This adds a {@code Set-Cookie} header with a value of {@code Cookie=parameter-value; Secure;
 * HttpOnly}
 */
@Component
public class RequestParameterToCookieGatewayFilterFactory
    extends AbstractGatewayFilterFactory<RequestParameterToCookieGatewayFilterFactory.Config> {

  public RequestParameterToCookieGatewayFilterFactory() {
    super(Config.class);
  }

  @Override
  public List<String> shortcutFieldOrder() {
    return List.of("parameter", "cookie");
  }

  @Override
  public GatewayFilter apply(final Config config) {
    return ((exchange, chain) ->
        chain.filter(exchange).then(Mono.fromRunnable(() -> execute(config, exchange))));
  }

  private void execute(final Config config, final ServerWebExchange exchange) {
    String value = exchange.getRequest().getQueryParams().getFirst(config.parameter());

    if (value != null) {
      exchange.getResponse().addCookie(create(config.cookie(), value));
    }
  }

  private ResponseCookie create(final String name, final String value) {
    return ResponseCookie.from(name, value).httpOnly(true).secure(true).sameSite("Strict").build();
  }

  public record Config(String parameter, String cookie) {}
}
